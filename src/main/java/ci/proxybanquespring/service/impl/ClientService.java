/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service.impl;

import ci.proxybanquespring.domaine.Customer;
import ci.proxybanquespring.domaine.Advisor;
import ci.proxybanquespring.repository.ClientRepository;
import ci.proxybanquespring.service.IClientService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author willi
 */
@Service
public class ClientService implements IClientService {

    //les proprietes
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Customer> readAllByConseiller(Advisor advisor) {

        if (advisor != null) {
            return clientRepository.findByConseillerAndEnabledTrue(advisor);
        }
        return null;

    }

    @Override
    public Customer create(Customer t) {

        if (t != null) {
            t.setDateCreation(new Date());
            t.setDateUpdate(new Date());
            t.setEnabled(true);
            return clientRepository.save(t);
        }
        return null;

    }

    @Override
    public List<Customer> readAll() {

        return clientRepository.findByEnabledTrue();

    }

    @Override
    public Customer readOne(Long pk) {

        if (pk > 0) {
            return clientRepository.findByIdAndEnabledTrue(pk);
        }
        return null;

    }

    @Override
    public Customer update(Customer t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            return clientRepository.save(t);
        }
        return null;

    }

    @Override
    public Boolean delete(Customer t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            t.setEnabled(false);
            return clientRepository.save(t) != null;
        }
        return false;

    }

}
