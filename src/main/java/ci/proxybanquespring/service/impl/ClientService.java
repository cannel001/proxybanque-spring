/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service.impl;

import ci.proxybanquespring.domaine.Client;
import ci.proxybanquespring.domaine.Conseiller;
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
    public List<Client> readAllByConseiller(Conseiller conseiller) {

        if (conseiller != null) {
            return clientRepository.findByConseillerAndEnabledTrue(conseiller);
        }
        return null;

    }

    public Client create(Client t) {

        if (t != null) {
            t.setDateCreation(new Date());
            t.setDateUpdate(new Date());
            t.setEnabled(true);
            return clientRepository.save(t);
        }
        return null;

    }

    public List<Client> readAll() {

        return clientRepository.findByEnabledTrue();

    }

    public Client readOne(Long pk) {

        if (pk > 0) {
            return clientRepository.findByIdAndEnabledTrue(pk);
        }
        return null;

    }

    
    public Client update(Client t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            return clientRepository.save(t);
        }
        return null;

    }

    
    public Boolean delete(Client t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            t.setEnabled(false);
            return clientRepository.save(t) != null;
        }
        return false;

    }

}
