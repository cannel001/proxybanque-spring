/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service.impl;

import ci.proxybanquespring.domaine.Versement;
import ci.proxybanquespring.service.IConsultationCompte;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
public class MesVersements implements IConsultationCompte {

    //les proprietes
    @Autowired
    private CourantService courantService;

    @Autowired
    private EpargneService epargneService;

    @Autowired
    private VersementService versementService;

    private Double montant = (double) 0;
    private String typCompte = "";
    private List<Versement> maList = null;

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
        if (typCompte.equals("courant")) {

            return calculVersCourant(compte);

        } else if (typCompte.equals("epargne")) {

            return calculVersEpargne(compte);

        }

        return montant;

    }

    //methode permettant de calculer les versement du compte courant
    public Double calculVersCourant(String compte) {

        maList = versementService.readAllVersementParCompte(compte);

        montant = (double) 0;
        
        //parcours de la liste
        for (Versement versement : maList) {

            montant += versement.getMontant();

        }

        return montant;

    }

    //methode permettant de calculer les versement du compte epargne
    public Double calculVersEpargne(String compte) {
        
        System.out.println("passage dans calcul epargne");
        

        maList = versementService.readAllVersementParCompte(compte);

        montant = (double) 0;
        
        //parcours de la liste
        for (Versement versement : maList) {

            montant += versement.getMontant();

        }

        System.out.println("montant retourne "+montant);
        
        return montant;

    }

}
