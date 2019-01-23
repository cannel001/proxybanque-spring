/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.controllers;

import ci.proxybanquespring.domaine.Client;
import ci.proxybanquespring.domaine.Conseiller;
import ci.proxybanquespring.domaine.Roles;
import ci.proxybanquespring.service.IClientService;
import ci.proxybanquespring.service.ICourantService;
import ci.proxybanquespring.service.IEpargneService;
import ci.proxybanquespring.service.ISendEmailService;
import ci.proxybanquespring.service.impl.ConseillerService;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author willi
 */
@Controller
public class IndexController {

    @Autowired
    private ConseillerService conseillerService;

    @Autowired
    private IClientService clientService;

    @Autowired
    private ICourantService courantService;

    @Autowired
    private IEpargneService epargneService;
    
    @Autowired
    private ISendEmailService sendEmailService;

    @GetMapping(value = "")
    public String index(Model model, HttpServletRequest request) {

        //recuperer le conseiller connecté
        String email = request.getRemoteUser();

        Conseiller conseillerConnecte = conseillerService.readOne(email);

        //recuperer la liste des clients par conseiller
        List<Client> listeClients;
        listeClients = clientService.readAllByConseiller(conseillerConnecte);

        //renvoyer les infos à la vue
        if (!listeClients.isEmpty()) {

            //recuperer le nombre de comptes par conseillers
            int nbCompte = courantService.countByConseiller(conseillerConnecte.getEmail()) + epargneService.countByConseiller(conseillerConnecte.getEmail());

            model.addAttribute("listeClients", listeClients);
            model.addAttribute("nbClients", listeClients.size());
            model.addAttribute("nbCompte", nbCompte);
        }

        return "index/index";
    }

    @GetMapping(value = "/login")
    public String connexion(Model model,HttpServletRequest request) {
        
        //recuperation des infos passés en parametres
        Object messageSucces=request.getSession().getAttribute("success");
        Object messageFailure=request.getSession().getAttribute("failure");
        
        if(messageSucces!=null){
            System.out.println("*************** passage success");
            model.addAttribute("success", messageSucces.toString());
            request.getSession().removeAttribute("success");
        }else if(messageFailure!=null){
            System.out.println("*************** passage error");
            model.addAttribute("failure", messageFailure.toString());
            request.getSession().removeAttribute("failure");
        }
        
        return "authentification/authentication-login";
    }

    @PostMapping(value = "/login/savenewcompte")
    public String saveIncription(HttpServletRequest request) {

        //recuperation des roles
        Set<Roles> roleUser = new HashSet<>();

        roleUser.add(new Roles("USER"));

        //recuperation des informations du formulaire
        String email = request.getParameter("email");
        String adresseIp = request.getRemoteAddr();

        //Generation d'un mot de passe par defaut
        String passwordGenere = conseillerService.genererPassword();

        System.out.println("************* mot de passe genere est " + passwordGenere);

        //verification de l'existance de l'email et de l'adresse ip
        if (conseillerService.readOne(email) != null) {
            //renvoyer un message à vue
            request.getSession().setAttribute("failure", "Ce email est déjà attribué à un autre conseiller");
            return "redirect:/login";
        } else {
            //inscription
            Conseiller conseiller = new Conseiller();
            conseiller.setEmail(email);
            conseiller.setPassword(conseillerService.cryptagePssword(passwordGenere));
            conseiller.setNom("user");
            conseiller.setPrenom("demo");
            conseiller.setRoleses(roleUser);

            if (conseillerService.create(conseiller) != null) {
                //envoi de l'email au client
                String nomDestinataire = "Moi";
                String emailDestinataire = conseiller.getEmail();
                String messageEmail = "La bienvenue sur la plateforme PROXY BANQUE G6\n\nInformations concernant"
                        + " votre compte conseiller"
                        + "\nEmail : " + conseiller.getEmail() + "\nDate d'ouverture : " + new Date() + "\nMot de passe : "
                        + passwordGenere;
                String sujet = "Ouverture de compte demo";
                
                //envoi du mail
                sendEmailService.sendMyEmail(nomDestinataire, emailDestinataire, messageEmail, sujet);
                request.getSession().setAttribute("success", "Votre compte a été creer avec succès. Veuillez consulter votre boite de messagerie pour recuperer les parametres de connexion");
                return "redirect:/login";
            }
        }

        return "redirect:/login";
    }

}
