/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.controllers;

import ci.proxybanquespring.service.IOperationService;
import ci.proxybanquespring.service.IVirementService;
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
@RequestMapping(value = "/virement")
public class VirementController {
    
    //les proprietes
    @Autowired
    private IVirementService virementService;
    
    @Autowired
    private IOperationService operationService;
    
    
    @GetMapping(value = "")
    public String formVirement(Model model,HttpServletRequest request){
        
        //verification et recuperation des objets de la session
        if(request.getSession().getAttribute("success")!=null){
            model.addAttribute("success", (String)request.getSession().getAttribute("success"));
            request.getSession().removeAttribute("success");
        }else if(request.getSession().getAttribute("failure")!=null){
            model.addAttribute("failure", (String)request.getSession().getAttribute("failure"));
            request.getSession().removeAttribute("failure");
        }
        
        return "virement/virement";
    }
    
    @GetMapping(value = "/confirm")
    public String confirVirement(){
        return "virement/confirmvirement";
    }
    
    @PostMapping(value = "/save")
    public String saveVirement(Model m,HttpServletRequest request){
        
        String cptDebiter=request.getParameter("cptDebiter");
        String cptCrediter=request.getParameter("cptCrediter");
        Double montant=Double.valueOf(request.getParameter("montant"));
        
        //verifier l'existance du account debiteur
        if(operationService.typeCompte(cptDebiter) == null){
            request.getSession().setAttribute("failure", "Le account debiteur n'existe pas");
            return "redirect:/virement";
        }
        
        //verifier l'existance du account crediteur
        if(operationService.typeCompte(cptCrediter) == null){
            request.getSession().setAttribute("failure", "Le account crediteur n'existe pas");
            return "redirect:/virement";
        }
        
        //verifier contrainte meme account
        if(cptCrediter.equals(cptDebiter)){
           request.getSession().setAttribute("failure", "Transfer impossible entre le meme account");
            return "redirect:/virement"; 
        }
        
        //verifier contrainte account à account
        if(!virementService.contrainteCompte(cptDebiter, cptCrediter)){
           request.getSession().setAttribute("failure", "Transfer impossible entre ces deux accounts");
            return "redirect:/virement"; 
        }
        
        //verifier le solde du account debiteur
        if(!operationService.verifSolde(cptDebiter, montant)){
           request.getSession().setAttribute("failure", "Le solde du account debiteur est insuffisant");
            return "redirect:/virement"; 
        }
        
        Long reference=virementService.virementCompteACompte(cptDebiter, cptCrediter, montant);
        
        if(reference!=null){
            request.getSession().setAttribute("success", "Transfer effectué avec succès");
        }else{
            request.getSession().setAttribute("failure", "Erreur inattendue survenue pendant l'operation");
        }
        
        return "redirect:/virement";
        
    }
    
    @GetMapping(value = "/resume")
    public String resumeeTransaction(){
        return "virement/resume";
    }
    
}
