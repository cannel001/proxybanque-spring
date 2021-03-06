/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service.impl;

import ci.proxybanquespring.domaine.Customer;
import ci.proxybanquespring.domaine.Payment;
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
    public List<Payment> readAllVersementByClient(Long idClient) {

        if (idClient > 0) {
            Customer client = clientService.readOne(idClient);
            if(client!=null){
                return versementRepository.findByCompteClientAndEnabledTrue(client);
            }
        }
        return null;

    }

    @Override
    public List<Payment> readAllVersementParCompte(String compte) {
//
//        if(!"".equals(account)){
//            return versementRepository.findByCompteAndStatut(account, Boolean.TRUE)
//        }

        return null;

    }

    @Override
    public Payment create(Payment t) {

        if (t != null) {
            t.setDateCreation(new Date());
            t.setDateUpdate(new Date());
            t.setEnabled(true);
            return versementRepository.save(t);
        }
        return null;

    }

    @Override
    public List<Payment> readAll() {

        return versementRepository.findByEnabledTrue();

    }

    @Override
    public Payment readOne(Long pk) {

        if (pk > 0) {
            return versementRepository.findByNumOperationAndEnabledTrue(pk);
        }
        return null;

    }

    
    public Payment update(Payment t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            return versementRepository.save(t);
        }
        return null;

    }

    @Override
    public Boolean delete(Payment t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            t.setEnabled(false);
            return versementRepository.save(t) != null;
        }
        return false;

    }

}
