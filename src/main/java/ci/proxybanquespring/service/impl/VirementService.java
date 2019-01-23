/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service.impl;

import ci.proxybanquespring.domaine.Client;
import ci.proxybanquespring.domaine.Courant;
import ci.proxybanquespring.domaine.Epargne;
import ci.proxybanquespring.domaine.Virement;
import ci.proxybanquespring.repository.VirementRepository;
import ci.proxybanquespring.service.IClientService;
import ci.proxybanquespring.service.ICourantService;
import ci.proxybanquespring.service.IEpargneService;
import ci.proxybanquespring.service.IOperationService;
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
    private ICourantService courantService;

    @Autowired
    private IEpargneService epargneService;

    @Autowired
    private IOperationService operationService;

    @Autowired
    private NumeroTransactionService numeroTransactionService;
    
    @Autowired
    private IClientService clientService;

    @Override
    public Boolean retirer(Virement virement) {

        if (virement != null) {
            virement.setTypVirement("RETIRER");
            return create(virement) != null;
        }

        return false;
    }

    @Override
    public Long virementCompteACompte(String cptDebit, String cptCredit, Double montant) {

        if (!"".equals(cptDebit) && !"".equals(cptCredit) && montant > 0) {

            Long reference = Long.valueOf((String) numeroTransactionService.generate());
            Virement virementDebit;
            Virement virementCredit;

            //creation de l'objet virementdebiter
            if (courantService.verifNumeroCompte(cptDebit)) {
                Courant courant = courantService.readOne(cptDebit);
                virementDebit = new Virement();
                virementDebit.setCompte(courant);
                virementDebit.setAncienSolde(courant.getSolde());
                virementDebit.setNouveauSolde(courant.getSolde() - montant);
                //update du compte courant
                courant.setSolde(courant.getSolde() - montant);
                courantService.update(courant);
            } else {
                Epargne epargne = epargneService.readOne(cptDebit);
                virementDebit = new Virement();
                virementDebit.setCompte(epargne);
                virementDebit.setAncienSolde(epargne.getSolde());
                virementDebit.setNouveauSolde(epargne.getSolde() - montant);
                //update du compte epargne
                epargne.setSolde(epargne.getSolde() - montant);
                epargneService.update(epargne);
            }

            //creation de l'objet virementCrediter
            if (courantService.verifNumeroCompte(cptCredit)) {
                Courant courant = courantService.readOne(cptCredit);
                virementCredit = new Virement();
                virementCredit.setCompte(courant);
                virementCredit.setAncienSolde(courant.getSolde());
                virementCredit.setNouveauSolde(courant.getSolde() + montant);
                //update du compte courant
                courant.setSolde(courant.getSolde() + montant);
                courantService.update(courant);

            } else {
                Epargne epargne = epargneService.readOne(cptCredit);
                virementCredit = new Virement();
                virementCredit.setCompte(epargne);
                virementCredit.setAncienSolde(epargne.getSolde());
                virementCredit.setNouveauSolde(epargne.getSolde() + montant);
                //update du compte epargne
                epargne.setSolde(epargne.getSolde() + montant);
                epargneService.update(epargne);
            }

            virementDebit.setReference(reference);
            virementDebit.setMontant(montant);
            virementDebit.setDateCreation(new Date());
            virementDebit.setDateUpdate(new Date());
            virementDebit.setDateOperation(new Date());
            virementDebit.setEnabled(true);
            virementDebit.setNote("Virement de compte à compte");
            virementDebit.setNumOperation(Long.valueOf(numeroTransactionService.generate().toString()));

            virementCredit.setReference(reference);
            virementCredit.setMontant(montant);
            virementCredit.setDateCreation(new Date());
            virementCredit.setDateUpdate(new Date());
            virementCredit.setDateOperation(new Date());
            virementCredit.setEnabled(true);
            virementCredit.setNote("Virement de compte à compte");
            virementCredit.setNumOperation(Long.valueOf(numeroTransactionService.generate().toString()));

            if (retirer(virementDebit).equals(true) && verser(virementCredit).equals(true)) {
                return reference;
            }

            return null;
        }

        return null;

    }

    @Override
    public Boolean contrainteCompte(String cpt1, String cpt2) {

        if (!"".equals(cpt1) && !"".equals(cpt2)) {
            //les proprietes
            Courant courant = null;
            Boolean responseContrainte = true;

            //recuperer les types comptes des deux comptes
            String typeCompt1 = operationService.typeCompte(cpt1);
            String typeCompt2 = operationService.typeCompte(cpt2);

            if (typeCompt1 != null && typeCompt2 != null) {

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

            }

            return responseContrainte;
        }
        return false;

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

    @Override
    public Virement create(Virement t) {

        if (t != null) {
            t.setDateCreation(new Date());
            t.setDateUpdate(new Date());
            t.setEnabled(true);
            return virementRepository.save(t);
        }
        return null;

    }

    @Override
    public List<Virement> readAll() {

        return virementRepository.findByEnabledTrue();

    }

    @Override
    public Virement readOne(Long pk) {

        if (pk > 0) {
            return virementRepository.findByNumOperationAndEnabledTrue(pk);
        }
        return null;

    }

    @Override
    public Virement update(Virement t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            return virementRepository.save(t);
        }
        return null;

    }

    @Override
    public Boolean delete(Virement t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            t.setEnabled(true);
            return virementRepository.save(t) != null;
        }
        return null;

    }

    @Override
    public Boolean verser(Virement virement) {

        if (virement != null) {
            virement.setTypVirement("VERSER");
            return create(virement) != null;
        }
        return false;
    }

    @Override
    public List<Virement> readAllVirementByClientAndRetirer(Long idClient) {

        if (idClient > 0) {
            Client client = clientService.readOne(idClient);
            if(client!=null){
                return virementRepository.findByCompteClientAndEnabledTrueAndTypVirement(client, "RETIRER");
            }
        }
        return null;

    }

    @Override
    public List<Virement> readAllVirementByClientAndVerser(Long idClient) {

        if (idClient > 0) {
            Client client = clientService.readOne(idClient);
            if(client!=null){
                return virementRepository.findByCompteClientAndEnabledTrueAndTypVirement(client, "VERSER");
            }
        }
        return null;

    }
    
    

}
