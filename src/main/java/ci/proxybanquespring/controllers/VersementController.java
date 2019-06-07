/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.controllers;

import ci.proxybanquespring.domaine.Current;
import ci.proxybanquespring.domaine.Savings;
import ci.proxybanquespring.domaine.Payment;
import ci.proxybanquespring.service.ICourantService;
import ci.proxybanquespring.service.IEpargneService;
import ci.proxybanquespring.service.IOperationService;
import ci.proxybanquespring.service.IVersementService;

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
        
        model.addAttribute("versement", new Payment());
        
        return "versement/formversement";
    }

    @PostMapping(value = "/save")
    public String confirmVersement(Payment v, Model m, HttpServletRequest request) {

        //verifier si le account existe
        String numCpt=request.getParameter("numCpt");
        String typCompte = operationService.typeCompte(numCpt);

        if (typCompte!=null) {

            //affectation de numero de transaction
            v.setNumOperation(operationService.generateNumTransc());
            v.setDateOperation(new Date());
            

            //recuperation du account
            if (typCompte.equals("Current")) {
                Current currentRecup = courantService.readOne(numCpt);
                v.setAccount(currentRecup);
                //ajout de l'ancien et nouveau solde
                v.setAncienSolde(currentRecup.getSolde());
                v.setNouveauSolde(currentRecup.getSolde() + v.getMontant());
                v.setNote("Rechargement du account courant "+ currentRecup.getNumCpt());
                //mise a jour du solde
                currentRecup.setSolde(currentRecup.getSolde() + v.getMontant());
                courantService.update(currentRecup);
            } else {
                Savings savingsRecup = epargneService.readOne(numCpt);
                v.setAccount(savingsRecup);
                //ajout de l'ancien et nouveau solde
                v.setAncienSolde(savingsRecup.getSolde());
                v.setNouveauSolde(savingsRecup.getSolde() + v.getMontant());
                v.setNote("Rechargement du account epargne "+ savingsRecup.getNumCpt());
                //mise a jour du solde
                savingsRecup.setSolde(savingsRecup.getSolde() + v.getMontant());
                epargneService.update(savingsRecup);
            }

            Payment versementretourne = versementService.create(v);

            if (versementretourne != null) {
                request.getSession().setAttribute("success", "Le versement de "+versementretourne.getMontant()+" effectué avec succès vers le account "+numCpt);
            }else{
                request.getSession().setAttribute("failure", "Erreur survenue pendant l'operation");
            }
        }else{
            request.getSession().setAttribute("failure", "Operation echoué car le account "+numCpt+" n'existe pas");
        }

        return "redirect:/versement";
    }

    @PostMapping(value = "/resumeetransaction")
    public String resumeeTransaction() {
        return "";
    }

}
