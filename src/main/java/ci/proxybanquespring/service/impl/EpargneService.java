/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service.impl;

import ci.proxybanquespring.domaine.Customer;
import ci.proxybanquespring.domaine.Savings;
import ci.proxybanquespring.repository.EpargneRepository;
import ci.proxybanquespring.service.IEpargneService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author willi
 */
@Service
public class EpargneService implements IEpargneService{
    
    //les proprietes
    @Autowired
    private EpargneRepository epargneRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ConseillerService conseillerService;

    @Override
    public String generateNumeroCompte() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean verifNumeroCompte(String numero) {

        if (!"".equals(numero)) {
            return epargneRepository.findByNumCptAndEnabledTrue(numero) != null;
        }
        return false;

    }

    @Override
    public List<Savings> readAllParClient(Long idClient) {

        if (idClient > 0) {
            Customer client = clientService.readOne(idClient);
            return epargneRepository.findByClientAndEnabledTrue(client);
        }
        return null;

    }

    @Override
    public int countByConseiller(String email) {

        int cpt=0;

        if (!"".equals(email)) {
            
            //recuperation de la liste de tous les clients
            List<Customer> listClient=clientService.readAllByConseiller(conseillerService.readOne(email));
            
            //compter le nombre des accounts pour chaque clients si la liste des clients est different de null
            if(!listClient.isEmpty()){
                
                for (Customer client : listClient) {
                    if(!readAllParClient(client.getId()).isEmpty()){
                        cpt+=readAllParClient(client.getId()).size();
                    }
                }
            }
            
        }
        return cpt;

    }

    @Override
    public Savings create(Savings t) {

        if (t != null) {
            t.setDateCreation(new Date());
            t.setDateUpdate(new Date());
            t.setEnabled(true);
            return epargneRepository.save(t);
        }
        return null;

    }

    @Override
    public List<Savings> readAll() {

        return epargneRepository.findByEnabledTrue();

    }

    
    public Savings readOne(String pk) {

        if (!"".equals(pk)) {
            return epargneRepository.findByNumCptAndEnabledTrue(pk);
        }
        return null;

    }

    @Override
    public Savings update(Savings t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            return epargneRepository.save(t);
        }
        return null;

    }

    @Override
    public Boolean delete(Savings t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            t.setEnabled(false);
            return epargneRepository.save(t) != null;
        }
        return false;

    }
    
}
