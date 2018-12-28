/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service.impl;

import ci.proxybanquespring.domaine.Client;
import ci.proxybanquespring.domaine.Versement;
import ci.proxybanquespring.repository.VersementRepository;
import ci.proxybanquespring.service.IVersementService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author willi
 */
@Service
public class VersementService implements IVersementService {

    //les proprietes
    @Autowired
    private VersementRepository versementRepository;

    @Autowired
    private NumeroTransactionService numeroTransactionService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private CourantService courantService;

    @Override
    public Long generateNumTransc() {

        Long newNum = Long.parseLong((String) numeroTransactionService.generate());

        while (versementRepository.findByNumOperationAndEnabledTrue(newNum) != null) {
            newNum = Long.parseLong((String) numeroTransactionService.generate());
        }

        return newNum;

    }

    @Override
    public List<Versement> readAllVersementPrClient(Long idClient) {

//        if (idClient > 0) {
//            Client client = clientService.readOne(idClient);
//            return versementRepository.findByClientAndStatut(client, true);
//        }
        return null;

    }

    @Override
    public String typeCompte(String numeroCpt) {

        if (!"".equals(numeroCpt)) {
            if (courantService.readOne(numeroCpt) != null) {
                return "Courant";
            }
            return "Epargne";
        }
        return null;

    }

    @Override
    public List<Versement> readAllVersementParCompte(String compte) {
//
//        if(!"".equals(compte)){
//            return versementRepository.findByCompteAndStatut(compte, Boolean.TRUE)
//        }

        return null;

    }

    
    public Versement create(Versement t) {

        if (t != null) {
            t.setDateCreation(new Date());
            t.setDateUpdate(new Date());
            t.setEnabled(true);
            return versementRepository.save(t);
        }
        return null;

    }

    
    public List<Versement> readAll() {

        return versementRepository.findByEnabledTrue();

    }

    
    public Versement readOne(Long pk) {

        if (pk > 0) {
            return versementRepository.findByNumOperationAndEnabledTrue(pk);
        }
        return null;

    }

    
    public Versement update(Versement t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            return versementRepository.save(t);
        }
        return null;

    }

    
    public Boolean delete(Versement t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            t.setEnabled(false);
            return versementRepository.save(t) != null;
        }
        return false;

    }

}
