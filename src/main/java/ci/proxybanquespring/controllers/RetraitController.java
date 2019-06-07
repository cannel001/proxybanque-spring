/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.controllers;

import ci.proxybanquespring.domaine.Current;
import ci.proxybanquespring.domaine.Savings;
import ci.proxybanquespring.domaine.WithDrawal;
import ci.proxybanquespring.service.ICourantService;
import ci.proxybanquespring.service.IEpargneService;
import ci.proxybanquespring.service.IOperationService;
import ci.proxybanquespring.service.IRetraitsService;
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
@RequestMapping(value = "/retrait")
public class RetraitController {

    //les proprietes
    @Autowired
    private IOperationService operationService;

    @Autowired
    private ICourantService courantService;

    @Autowired
    private IEpargneService epargneService;

    @Autowired
    private IRetraitsService retraitsService;

    @GetMapping(value = "")
    public String formRetrait(Model m, HttpServletRequest request) {

        //verification et recuperation des objets de la session
        if (request.getSession().getAttribute("success") != null) {
            m.addAttribute("success", (String) request.getSession().getAttribute("success"));
            request.getSession().removeAttribute("success");
        } else if (request.getSession().getAttribute("failure") != null) {
            m.addAttribute("failure", (String) request.getSession().getAttribute("failure"));
            request.getSession().removeAttribute("failure");
        }

        m.addAttribute("retrait", new WithDrawal());

        return "retrait/formretrait.html";
    }

    @PostMapping(value = "/save")
    public String saveRetrait(WithDrawal r, Model m, Principal p, HttpServletRequest request) {

        //verifier si le account existe
        String numCpt = request.getParameter("numCpt");
        String typCompte = operationService.typeCompte(numCpt);

        if (typCompte != null) {

            //verification du solde
            if (!operationService.verifSolde(numCpt, r.getMontant())) {
                request.getSession().setAttribute("failure", "Le solde de ce account est inferieur au montant à retirer");
                return "redirect:/retrait";
            }

            //affectation de numero de transaction
            r.setNumOperation(operationService.generateNumTransc());
            r.setDateOperation(new Date());

            //recuperation du account
            if (typCompte.equals("Current")) {
                Current currentRecup = courantService.readOne(numCpt);
                r.setAccount(currentRecup);
                //ajout de l'ancien et nouveau solde
                r.setAncienSolde(currentRecup.getSolde());
                r.setNouveauSolde(currentRecup.getSolde() - r.getMontant());
                r.setNote("Retrait à partir du account courant " + currentRecup.getNumCpt());
                //mise a jour du solde
                currentRecup.setSolde(currentRecup.getSolde() - r.getMontant());
                courantService.update(currentRecup);
            } else {
                Savings savingsRecup = epargneService.readOne(numCpt);
                r.setAccount(savingsRecup);
                //ajout de l'ancien et nouveau solde
                r.setAncienSolde(savingsRecup.getSolde());
                r.setNouveauSolde(savingsRecup.getSolde() - r.getMontant());
                r.setNote("Retrait à partir du account epargne " + savingsRecup.getNumCpt());
                //mise a jour du solde
                savingsRecup.setSolde(savingsRecup.getSolde() - r.getMontant());
                epargneService.update(savingsRecup);
            }

            WithDrawal retraitRetournee = retraitsService.create(r);

            if (retraitRetournee != null) {
                request.getSession().setAttribute("success", "Operation effectuée avec succès");
            } else {
                request.getSession().setAttribute("failure", "Erreur inattendue survenue pendant l'operation. Veuillez "
                        + "relancer le traitement s'il vous plait");
            }

        } else {
            request.getSession().setAttribute("failure", "Operation echouée car le account " + numCpt + " n'existe pas");
        }

        return "redirect:/retrait";

    }

}
