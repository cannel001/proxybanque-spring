/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.controllers;

import ci.proxybanquespring.domaine.Client;
import ci.proxybanquespring.domaine.Conseiller;
import ci.proxybanquespring.domaine.Courant;
import ci.proxybanquespring.domaine.Epargne;
import ci.proxybanquespring.domaine.Retraits;
import ci.proxybanquespring.domaine.Versement;
import ci.proxybanquespring.domaine.Virement;
import ci.proxybanquespring.service.IClientService;
import ci.proxybanquespring.service.IConseillerService;
import ci.proxybanquespring.service.ICourantService;
import ci.proxybanquespring.service.IEpargneService;
import ci.proxybanquespring.service.IRetraitsService;
import ci.proxybanquespring.service.ISendEmailService;
import ci.proxybanquespring.service.IVersementService;
import ci.proxybanquespring.service.IVirementService;
import ci.proxybanquespring.service.impl.IbanCiService;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
@RequestMapping(value = "/client")
public class ClientController {
    
    //les proprietes
    @Autowired
    private IConseillerService conseillerService;
    
    @Autowired
    private IClientService clientService;
    
    @Autowired
    private ICourantService courantService;
    
    @Autowired
    private IVersementService versementService;
    
    @Autowired
    private IRetraitsService retraitService;
    
    @Autowired
    private IVirementService virementService;
    
    @Autowired
    private IbanCiService ibanCiService;
    
    @Autowired
    private ISendEmailService sendEmailService;
    
    @Autowired
    private IEpargneService epargneService;
    
    
    @GetMapping(value = "/formclient")
    public String formNouvClient(Model m,HttpServletRequest request){
        
        //verification et recuperation des objets de la session
        if(request.getSession().getAttribute("success")!=null){
            m.addAttribute("success", (String)request.getSession().getAttribute("success"));
            request.getSession().removeAttribute("success");
        }else if(request.getSession().getAttribute("failure")!=null){
            m.addAttribute("failure", (String)request.getSession().getAttribute("failure"));
            request.getSession().removeAttribute("failure");
        }
        
        m.addAttribute("courant", new Courant());
        
        return "client/formulaireclient";
    }
    
    @PostMapping(value = "/save")
    public String saveClient(Courant courant,Model m,Principal p){
        
        //recuperation du l'user connecté
        Conseiller conseiller=conseillerService.readOneByEmail(p.getName());
        
        //recuperer le client
        Client c=courant.getClient();
        
        //enregistrement du client
        c.setConseiller(conseiller);
        
        
        Client clientRetourne=clientService.create(c);
        if(clientRetourne!=null){
            
            //enregistrement du compte courant
            courant.setClient(clientRetourne);
            courant.setSolde(courant.getMontantInitial());
            courant.setNumCpt(ibanCiService.generate().toString());

            if(courantService.create(courant)!=null){
                
                //envoi de l'email au client
                String nomDestinataire = "Moi";
                String emailDestinataire = clientRetourne.getEmail();
                String messageEmail = "La bienvenue sur la plateforme PROXY BANQUE G6\n\nInformations concernant"
                        + " votre compte courant"
                        + "\nIBAN : " + courant.getNumCpt() + "\nDate d'ouverture : " + new Date() + "\nMontant : "
                        + courant.getMontantInitial() + "\nSolde : "
                        + courant.getSolde();
                String sujet = "Ouverture de compte courant";
                
                //envoi du mail
                sendEmailService.sendMyEmail(nomDestinataire, emailDestinataire, messageEmail, sujet);
                
                return "redirect:/client/formclient";
            }
        }
        
        return null;
    }
    
    @GetMapping(value = "/profile/{id}")
    public String afficherProfilClient(Model m,@PathVariable Long id,HttpServletRequest request){
        
        //recuperation des objets de la session
        Object success=request.getSession().getAttribute("success");
        Object failure=request.getSession().getAttribute("failure");
        
        if(success!=null){
            m.addAttribute("success", success.toString());
            request.getSession().removeAttribute("success");
        }else if(failure!=null){
            m.addAttribute("failure", failure.toString());
            request.getSession().removeAttribute("failure");
        }
        
        //les proprietes
        Optional<List<Courant>> maListe=null;        
        List<Epargne> listEpargne;
        
        //rechercher le client par id
        Optional<Client> client=Optional.of(clientService.readOne(id));
                
        //rechercher le compte courant du client
        if(client.isPresent()){
            //recuperation des versements par ce client
            List<Versement> listVersement=versementService.readAllVersementByClient(client.get().getId());
            if(!listVersement.isEmpty()){
                m.addAttribute("versements", listVersement);
            }
            
            //recuperation des retraits effectués par ce client
            List<Retraits> listRetraits=retraitService.readAllRetraitByClient(client.get().getId());
            if(!listRetraits.isEmpty()){
                m.addAttribute("retraits", listRetraits);
            }
            
            //recuperation des virements recu par ce client
            List<Virement> listVirementRecu=virementService.readAllVirementByClientAndVerser(client.get().getId());
            if(!listVirementRecu.isEmpty()){
                m.addAttribute("virementsrecu", listVirementRecu);
            }
            
            //recuperation des virements envoyé par ce client
            List<Virement> listVirementEnvoye=virementService.readAllVirementByClientAndRetirer(client.get().getId());
            if(!listVirementEnvoye.isEmpty()){
                m.addAttribute("virementsenvoye", listVirementEnvoye);
            }
            
            maListe=Optional.of(courantService.readAllParClient(client.get().getId()));
            
            //recherche et recuperation des comptes epargnes du client
            listEpargne=epargneService.readAllParClient(client.get().getId());
            if(!listEpargne.isEmpty()){
                m.addAttribute("epargnes", listEpargne);
            }
            
        }
        
        if(client.isPresent() && maListe!=null){
            m.addAttribute("client", client);
            m.addAttribute("courant", maListe.get().get(0));
            return "client/client";
        }
        return "/client";
        
    }
    
    @GetMapping(value = "/modifclient")
    public String formModifClient(){
        return "client/formulaireclient";
    }
    
}
