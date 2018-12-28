/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service.impl;

import ci.proxybanquespring.domaine.Courant;
import ci.proxybanquespring.domaine.Virement;
import ci.proxybanquespring.repository.VirementRepository;
import ci.proxybanquespring.service.IVirementService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author willi
 */
@Service
public class VirementService implements IVirementService {

    //les proprietes
    @Autowired
    private VirementRepository virementRepository;

    @Autowired
    private CourantService courantService;
    
    @Autowired
    private NumeroTransactionService numeroTransactionService;

    @Override
    public Boolean retirer(Virement virement) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean verifSolde(String cpt, Double montant) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long virementCompteACompte(String cptDebit, String cptCredit, Double montant) {

        if (!"".equals(cptDebit) && !"".equals(cptCredit) && montant > 0) {
            Long reference = Long.valueOf((String) numeroTransactionService.generate());
            Virement virementDebit;
            Virement virementCredit;

//            //creation de l'objet virementdebiter
//            if (courantService.verifNumeroCompte(cptDebit)) {
//                virementDebit = new Virement(Long.valueOf((String) numeroTransactionService.generate()), new Date(), montant, courantService.readOne(cptDebit));
//            } else {
//                virementDebit = new Virement(Long.valueOf((String) numeroTransactionService.generate()), new Date(), montant, epargneService.readOne(cptDebit));
//            }
//
//            //creation de l'objet virementCrediter
//            if (courantService.verifNumeroCompte(cptCredit)) {
//                virementCredit = new Virement(Long.valueOf((String) numeroTransactionService.generate()), new Date(), montant, courantService.readOne(cptCredit));
//            } else {
//                virementCredit = new Virement(Long.valueOf((String) numeroTransactionService.generate()), new Date(), montant, epargneService.readOne(cptCredit));
//            }

//            virementDebit.setReference(reference);
//            virementDebit.setDateCreation(new Date());
//            virementDebit.setDateUpdate(new Date());
//            virementDebit.setStatut(true);
//            
//            virementCredit.setReference(reference);
//            virementCredit.setDateCreation(new Date());
//            virementCredit.setDateUpdate(new Date());
//            virementCredit.setStatut(true);

//            if (virementRepository.save(virementDebit)!=null && virementRepository.save(virementCredit)!=null) {
//
//                return reference;
//
//            }

            return (long) 0;
        }
        
        return (long)0;

    }

    @Override
    public Boolean contrainteCompte(String cpt1, String cpt2) {

        if (!"".equals(cpt1) && !"".equals(cpt2)) {
            //les proprietes
            Courant courant = null;
            Boolean responseContrainte = true;

            String typeCompt1;
            String typeCompt2;

            //recuperer les types comptes des deux comptes
            //cpt1
            if (courantService.verifNumeroCompte(cpt1)) {
                typeCompt1 = "courant";
            } else {
                typeCompt1 = "epargne";
            }

            //cpt2
            if (courantService.verifNumeroCompte(cpt2)) {
                typeCompt2 = "courant";
            } else {
                typeCompt2 = "epargne";
            }

            //verification des comptes
            //Meme numero de compte
            if (cpt1.equals(cpt2)) {
                responseContrainte = false;
            }

            //verification des types
            if (typeCompt1.equals("epargne") && typeCompt2.equals("courant")) {
                responseContrainte = false;
            }

            if (typeCompt1.equals("epargne") && typeCompt2.equals("epargne")) {
                responseContrainte = false;
            }

            return responseContrainte;
        }
        return false;

    }

    @Override
    public List<Virement> readAllVirementParCompte(String compte) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Virement> readAllVirementRetirerParCompte(String compte) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Virement> readAllVirementPrClient(Long idClient) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Virement> readAllVirementVerserParCompte(String compte) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Virement> readAllVirementVirePrClient(Long idClient) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Virement> readAllVirementRecuPrClient(Long idClient) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Virement readOneVirementVireParRef(Long reference) {

        if (reference > 0) {
            return virementRepository.findByReferenceAndTypVirementAndEnabledTrue(reference, "RETIRER");
        }
        return null;

    }

    @Override
    public Virement readOneVirementRecuParRef(Long reference) {

        if (reference > 0) {
            return virementRepository.findByReferenceAndTypVirementAndEnabledTrue(reference, "VERSER");
        }
        return null;

    }

    
    public Virement create(Virement t) {

        if (t != null) {
            t.setDateCreation(new Date());
            t.setDateUpdate(new Date());
            t.setEnabled(true);
            return virementRepository.save(t);
        }
        return null;

    }

    
    public List<Virement> readAll() {

        return virementRepository.findByEnabledTrue();

    }

    
    public Virement readOne(Long pk) {

        if (pk > 0) {
            return virementRepository.findByNumOperationAndEnabledTrue(pk);
        }
        return null;

    }

    
    public Virement update(Virement t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            return virementRepository.save(t);
        }
        return null;

    }

    
    public Boolean delete(Virement t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            t.setEnabled(true);
            return virementRepository.save(t) != null;
        }
        return null;

    }

}
