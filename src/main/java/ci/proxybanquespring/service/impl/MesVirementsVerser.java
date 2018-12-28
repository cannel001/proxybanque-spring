/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service.impl;

import ci.proxybanquespring.domaine.Courant;
import ci.proxybanquespring.domaine.Epargne;
import ci.proxybanquespring.domaine.Virement;
import ci.proxybanquespring.service.IConsultationCompte;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
public class MesVirementsVerser implements IConsultationCompte{

    //les proprietes
    @Autowired
    private CourantService courantService;

    @Autowired
    private EpargneService epargneService;

    @Autowired
    private VirementService virementService;

    private Courant courant = null;
    private Epargne epargne = null;
    private double montant =0;
    private String typCompte = ""; 
    private List<Virement> maList = new LinkedList<>();

    @Override
    public Double getTotal(String compte) {

        //recuperation du type de compte
        if (courantService.readOne(compte) != null) {
            typCompte = "courant";
        }

        if (epargneService.readOne(compte) != null) {
            typCompte = "epargne";
        }

        //traitement
        if(typCompte.equals("courant")){
            
            montant=0;
            montant=calculVirVerserCourant(compte);
            
        }else if(typCompte.equals("epargne")){
            
            montant=0;
            montant=calculVirVerserCourant(compte);
            
        }
        
        return montant;

    }

    //methode permettant de calculer les versement du compte courant
    public Double calculVirVerserCourant(String compte) {

        double montanttt=0;
        
        if (typCompte.equals("courant")) {

            courant = courantService.readOne(compte);

            maList = virementService.readAllVirementVerserParCompte(compte);

            //parcours de la liste
            for (Virement virement : maList) {

                montanttt += virement.getMontant();

            }

        }

        return montanttt;

    }
    
    //methode permettant de calculer les versement du compte epargne
    public Double calculVirVerserEpargne(String compte) {

        double montanttt=0;
        
        if (typCompte.equals("epargne")) {

            epargne = epargneService.readOne(compte);
            
            maList = virementService.readAllVirementVerserParCompte(compte);

            //parcours de la liste
            for (Virement virement : maList) {

                montanttt += virement.getMontant();

            }

        }

        return montanttt;

    }
    
}
