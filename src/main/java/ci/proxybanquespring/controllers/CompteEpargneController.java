/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.controllers;

import ci.proxybanquespring.domaine.Client;
import ci.proxybanquespring.domaine.Epargne;
import ci.proxybanquespring.service.IClientService;
import ci.proxybanquespring.service.IEpargneService;
import ci.proxybanquespring.service.ISendEmailService;
import ci.proxybanquespring.service.impl.IbanCiService;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author willi
 */
@Controller
@RequestMapping("epargne")
public class CompteEpargneController {
    
    //les proprietes
    @Autowired
    private IEpargneService epargneService;
    
    @Autowired
    private IbanCiService ibanService;
    
    @Autowired
    private ISendEmailService sendEmailService;
    
    @Autowired
    private IClientService clientService;
    
    @GetMapping(value = "/{id}")
    public String afficherFormEpargne(@PathVariable Long id,Model model,HttpServletRequest request){
        
        //recuperation des infos de la requete
        Client client=clientService.readOne(id);
        
        System.out.println("********************** client "+client);
        
        //ajouter l'object à la session
        request.getSession().setAttribute("client", client);
        model.addAttribute("clientrecup", client);
                
        return "epargne/formepargne.html";
    }
    
    @PostMapping(value = "/save")
    public String saveEpargne(Model model,HttpServletRequest request){
        Float taux=Float.valueOf(request.getParameter("taux"));
        Object objectRecuperer=request.getSession().getAttribute("client");
        Client clientRecuperer = new Client();
        if(objectRecuperer!=null){
            request.getSession().removeAttribute("client");
            clientRecuperer=(Client)objectRecuperer;
            Epargne epargne=new Epargne();
            epargne.setNumCpt(ibanService.generate().toString());
            epargne.setClient(clientRecuperer);
            epargne.setTaux(taux);
            if(epargneService.create(epargne)!=null){
                
                //envoi de l'email au client
                String nomDestinataire = "Moi";
                String emailDestinataire = clientRecuperer.getEmail();
                String messageEmail = "La bienvenue sur la plateforme PROXY BANQUE G6\n\nInformations concernant"
                        + " votre compte epargne"
                        + "\nIBAN : " + epargne.getNumCpt() + "\nDate d'ouverture : " + new Date() + "\nSolde : "
                        + 0 + "\nTaux d'interet : "+epargne.getTaux();
                String sujet = "Ouverture de compte courant";
                
                //envoi du mail
                sendEmailService.sendMyEmail(nomDestinataire, emailDestinataire, messageEmail, sujet);
                
                request.getSession().setAttribute("success", "Creation du compte epargne effectué avec succès");
                
                
            }else{
                request.getSession().setAttribute("failure", "Erreur survenue pendant l'operation");
            }
        }
        return "redirect:/client/profile/"+clientRecuperer.getId();
    }
}
