/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service.impl;

import ci.proxybanquespring.domaine.Customer;
import ci.proxybanquespring.domaine.Current;
import ci.proxybanquespring.domaine.Savings;
import ci.proxybanquespring.domaine.Transfer;
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
    public Boolean retirer(Transfer transfer) {

        if (transfer != null) {
            transfer.setTypVirement("RETIRER");
            return create(transfer) != null;
        }

        return false;
    }

    @Override
    public Long virementCompteACompte(String cptDebit, String cptCredit, Double montant) {

        if (!"".equals(cptDebit) && !"".equals(cptCredit) && montant > 0) {

            Long reference = Long.valueOf((String) numeroTransactionService.generate());
            Transfer transferDebit;
            Transfer transferCredit;

            //creation de l'objet virementdebiter
            if (courantService.verifNumeroCompte(cptDebit)) {
                Current current = courantService.readOne(cptDebit);
                transferDebit = new Transfer();
                transferDebit.setAccount(current);
                transferDebit.setAncienSolde(current.getSolde());
                transferDebit.setNouveauSolde(current.getSolde() - montant);
                //update du account current
                current.setSolde(current.getSolde() - montant);
                courantService.update(current);
            } else {
                Savings savings = epargneService.readOne(cptDebit);
                transferDebit = new Transfer();
                transferDebit.setAccount(savings);
                transferDebit.setAncienSolde(savings.getSolde());
                transferDebit.setNouveauSolde(savings.getSolde() - montant);
                //update du account savings
                savings.setSolde(savings.getSolde() - montant);
                epargneService.update(savings);
            }

            //creation de l'objet virementCrediter
            if (courantService.verifNumeroCompte(cptCredit)) {
                Current current = courantService.readOne(cptCredit);
                transferCredit = new Transfer();
                transferCredit.setAccount(current);
                transferCredit.setAncienSolde(current.getSolde());
                transferCredit.setNouveauSolde(current.getSolde() + montant);
                //update du account current
                current.setSolde(current.getSolde() + montant);
                courantService.update(current);

            } else {
                Savings savings = epargneService.readOne(cptCredit);
                transferCredit = new Transfer();
                transferCredit.setAccount(savings);
                transferCredit.setAncienSolde(savings.getSolde());
                transferCredit.setNouveauSolde(savings.getSolde() + montant);
                //update du account savings
                savings.setSolde(savings.getSolde() + montant);
                epargneService.update(savings);
            }

            transferDebit.setReference(reference);
            transferDebit.setMontant(montant);
            transferDebit.setDateCreation(new Date());
            transferDebit.setDateUpdate(new Date());
            transferDebit.setDateOperation(new Date());
            transferDebit.setEnabled(true);
            transferDebit.setNote("Transfer de account à account");
            transferDebit.setNumOperation(Long.valueOf(numeroTransactionService.generate().toString()));

            transferCredit.setReference(reference);
            transferCredit.setMontant(montant);
            transferCredit.setDateCreation(new Date());
            transferCredit.setDateUpdate(new Date());
            transferCredit.setDateOperation(new Date());
            transferCredit.setEnabled(true);
            transferCredit.setNote("Transfer de account à account");
            transferCredit.setNumOperation(Long.valueOf(numeroTransactionService.generate().toString()));

            if (retirer(transferDebit).equals(true) && verser(transferCredit).equals(true)) {
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
            Current current = null;
            Boolean responseContrainte = true;

            //recuperer les types accounts des deux accounts
            String typeCompt1 = operationService.typeCompte(cpt1);
            String typeCompt2 = operationService.typeCompte(cpt2);

            if (typeCompt1 != null && typeCompt2 != null) {

                //verification des accounts
                //Meme numero de account
                if (cpt1.equals(cpt2)) {
                    responseContrainte = false;
                }

                //verification des types
                if (typeCompt1.equals("epargne") && typeCompt2.equals("current")) {
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
    public Transfer readOneVirementVireParRef(Long reference) {

        if (reference > 0) {
            return virementRepository.findByReferenceAndTypVirementAndEnabledTrue(reference, "RETIRER");
        }
        return null;

    }

    @Override
    public Transfer readOneVirementRecuParRef(Long reference) {

        if (reference > 0) {
            return virementRepository.findByReferenceAndTypVirementAndEnabledTrue(reference, "VERSER");
        }
        return null;

    }

    @Override
    public Transfer create(Transfer t) {

        if (t != null) {
            t.setDateCreation(new Date());
            t.setDateUpdate(new Date());
            t.setEnabled(true);
            return virementRepository.save(t);
        }
        return null;

    }

    @Override
    public List<Transfer> readAll() {

        return virementRepository.findByEnabledTrue();

    }

    @Override
    public Transfer readOne(Long pk) {

        if (pk > 0) {
            return virementRepository.findByNumOperationAndEnabledTrue(pk);
        }
        return null;

    }

    @Override
    public Transfer update(Transfer t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            return virementRepository.save(t);
        }
        return null;

    }

    @Override
    public Boolean delete(Transfer t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            t.setEnabled(true);
            return virementRepository.save(t) != null;
        }
        return null;

    }

    @Override
    public Boolean verser(Transfer transfer) {

        if (transfer != null) {
            transfer.setTypVirement("VERSER");
            return create(transfer) != null;
        }
        return false;
    }

    @Override
    public List<Transfer> readAllVirementByClientAndRetirer(Long idClient) {

        if (idClient > 0) {
            Customer client = clientService.readOne(idClient);
            if(client!=null){
                return virementRepository.findByCompteClientAndEnabledTrueAndTypVirement(client, "RETIRER");
            }
        }
        return null;

    }

    @Override
    public List<Transfer> readAllVirementByClientAndVerser(Long idClient) {

        if (idClient > 0) {
            Customer client = clientService.readOne(idClient);
            if(client!=null){
                return virementRepository.findByCompteClientAndEnabledTrueAndTypVirement(client, "VERSER");
            }
        }
        return null;

    }
    
    

}
