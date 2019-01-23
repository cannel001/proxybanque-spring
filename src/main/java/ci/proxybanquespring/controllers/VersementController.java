/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.controllers;

import ci.proxybanquespring.domaine.Courant;
import ci.proxybanquespring.domaine.Epargne;
import ci.proxybanquespring.domaine.Versement;
import ci.proxybanquespring.service.ICourantService;
import ci.proxybanquespring.service.IEpargneService;
import ci.proxybanquespring.service.IOperationService;
import ci.proxybanquespring.service.IVersementService;
import java.security.Principal;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author willi
 */
@Controller
@RequestMapping("versement")
public class VersementController {

    //les proprietes
    @Autowired
    private IVersementService versementService;

    @Autowired
    private IOperationService operationService;

    @Autowired
    private ICourantService courantService;

    @Autowired
    private IEpargneService epargneService;

    @GetMapping(value = "")
    public String formVersement(Model model,HttpServletRequest request) {
        
         //verification et recuperation des objets de la session
        if(request.getSession().getAttribute("success")!=null){
            model.addAttribute("success", (String)request.getSession().getAttribute("success"));
            request.getSession().removeAttribute("success");
        }else if(request.getSession().getAttribute("failure")!=null){
            model.addAttribute("failure", (String)request.getSession().getAttribute("failure"));
            request.getSession().removeAttribute("failure");
        }
        
        model.addAttribute("versement", new Versement());
        
        return "versement/formversement";
    }

    @PostMapping(value = "/save")
    public String confirmVersement(Versement v, Model m,HttpServletRequest request) {

        //verifier si le compte existe
        String numCpt=request.getParameter("numCpt");
        String typCompte = operationService.typeCompte(numCpt);

        if (typCompte!=null) {

            //affectation de numero de transaction
            v.setNumOperation(operationService.generateNumTransc());
            v.setDateOperation(new Date());
            

            //recuperation du compte
            if (typCompte.equals("Courant")) {
                Courant courantRecup = courantService.readOne(numCpt);
                v.setCompte(courantRecup);
                //ajout de l'ancien et nouveau solde
                v.setAncienSolde(courantRecup.getSolde());
                v.setNouveauSolde(courantRecup.getSolde() + v.getMontant());
                v.setNote("Rechargement du compte courant "+courantRecup.getNumCpt());
                //mise a jour du solde
                courantRecup.setSolde(courantRecup.getSolde() + v.getMontant());
                courantService.update(courantRecup);
            } else {
                Epargne epargneRecup = epargneService.readOne(numCpt);
                v.setCompte(epargneRecup);
                //ajout de l'ancien et nouveau solde
                v.setAncienSolde(epargneRecup.getSolde());
                v.setNouveauSolde(epargneRecup.getSolde() + v.getMontant());
                v.setNote("Rechargement du compte epargne "+epargneRecup.getNumCpt());
                //mise a jour du solde
                epargneRecup.setSolde(epargneRecup.getSolde() + v.getMontant());
                epargneService.update(epargneRecup);
            }

            Versement versementretourne = versementService.create(v);

            if (versementretourne != null) {
                request.getSession().setAttribute("success", "Le versement de "+versementretourne.getMontant()+" effectué avec succès vers le compte "+numCpt);
            }else{
                request.getSession().setAttribute("failure", "Erreur survenue pendant l'operation");
            }
        }else{
            request.getSession().setAttribute("failure", "Operation echoué car le compte "+numCpt+" n'existe pas");
        }

        return "redirect:/versement";
    }

    @PostMapping(value = "/resumeetransaction")
    public String resumeeTransaction() {
        return "";
    }

}
