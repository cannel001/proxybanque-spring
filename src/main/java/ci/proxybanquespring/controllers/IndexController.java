/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.controllers;

import ci.proxybanquespring.domaine.Advisor;
import ci.proxybanquespring.domaine.Customer;
import ci.proxybanquespring.domaine.Roles;
import ci.proxybanquespring.service.IClientService;
import ci.proxybanquespring.service.ICourantService;
import ci.proxybanquespring.service.IEpargneService;
import ci.proxybanquespring.service.ISendEmailService;
import ci.proxybanquespring.service.ISendSmsService;
import ci.proxybanquespring.service.impl.ConseillerService;
import java.util.Date;
import java.util.HashSet;
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
    
    @Autowired
    private ISendSmsService sendSmsService;

    @GetMapping(value = "")
    public String index(Model model, HttpServletRequest request) {

        //recuperer le advisor connecté
        String email = request.getRemoteUser();

        Advisor advisorConnecte = conseillerService.readOne(email);

        //recuperer la liste des clients par advisor
        List<Customer> listeClients;
        listeClients = clientService.readAllByConseiller(advisorConnecte);

        //renvoyer les infos à la vue
        if (!listeClients.isEmpty()) {

            //recuperer le nombre de accounts par conseillers
            int nbCompte = courantService.countByConseiller(advisorConnecte.getEmail()) + epargneService.countByConseiller(advisorConnecte.getEmail());

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
            request.getSession().setAttribute("failure", "Ce email est déjà attribué à un autre advisor");
            return "redirect:/login";
        } else {
            //inscription
            Advisor advisor = new Advisor();
            advisor.setEmail(email);
            advisor.setPassword(conseillerService.cryptagePssword(passwordGenere));
            advisor.setNom("user");
            advisor.setPrenom("demo");
            advisor.setRoleses(roleUser);

            if (conseillerService.create(advisor) != null) {
                //envoi de l'email au client
                String nomDestinataire = "Moi";
                String emailDestinataire = advisor.getEmail();
                String messageEmail = "La bienvenue sur la plateforme PROXY BANQUE G6\n\nInformations concernant"
                        + " votre account advisor"
                        + "\nEmail : " + advisor.getEmail() + "\nDate d'ouverture : " + new Date() + "\nMot de passe : "
                        + passwordGenere;
                String sujet = "Ouverture de account demo";
                
                //envoi du mail
                sendEmailService.sendMyEmail(nomDestinataire, emailDestinataire, messageEmail, sujet);
                
                //envoi d'un sms
                sendSmsService.sendMySms(request.getParameter("tel"), "Veuillez utiliser le code "+passwordGenere+" pour vous connecter sur la plateforme ProxyBanque");
                
                //sendSmsService.sendMySms(sujet, messageEmail)
                request.getSession().setAttribute("success", "Votre account a été creer avec succès. Veuillez consulter votre boite de messagerie pour recuperer les parametres de connexion");
                return "redirect:/login";
            }
        }

        return "redirect:/login";
    }

}
