/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service.impl;

import ci.proxybanquespring.domaine.Customer;
import ci.proxybanquespring.domaine.WithDrawal;
import ci.proxybanquespring.repository.RetraitsRepository;
import ci.proxybanquespring.service.IClientService;
import ci.proxybanquespring.service.IRetraitsService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author willi
 */
@Service
public class RetraitsService implements IRetraitsService{
    
    //les proprietes
    @Autowired
    private RetraitsRepository repository;
    
    @Autowired
    private IClientService clientService;
    
    @Override
     public WithDrawal create(WithDrawal t) {

        if (t != null) {
            t.setDateCreation(new Date());
            t.setDateUpdate(new Date());
            t.setEnabled(true);
            return repository.save(t);
        }
        return null;

    }

     @Override
    public List<WithDrawal> readAll() {

        return repository.findByEnabledTrue();

    }

    @Override
    public WithDrawal readOne(Long numOperation) {

        if (numOperation > 0) {
            return repository.findByNumOperationAndEnabledTrue(numOperation);
        }
        return null;

    }

    @Override
    public WithDrawal update(WithDrawal t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            return repository.save(t);
        }
        return null;

    }

    @Override
    public Boolean delete(WithDrawal t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            t.setEnabled(false);
            return repository.save(t) != null;
        }
        return false;

    }

    @Override
    public List<WithDrawal> readAllRetraitByClient(Long idClient) {

        if (idClient > 0) {
            Customer client = clientService.readOne(idClient);
            if(client!=null){
                return repository.findByCompteClientAndEnabledTrue(client);
            }
        }
        return null;

    }
    
    
    
}
