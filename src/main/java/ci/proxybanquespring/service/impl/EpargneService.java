/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service.impl;

import ci.proxybanquespring.domaine.Client;
import ci.proxybanquespring.domaine.Epargne;
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
    public List<Epargne> readAllParClient(Long idClient) {

        if (idClient > 0) {
            Client client = clientService.readOne(idClient);
            return epargneRepository.findByClientAndEnabledTrue(client);
        }
        return null;

    }

    @Override
    public int countByConseiller(String email) {

        int cpt=0;

        if (!"".equals(email)) {
            
            //recuperation de la liste de tous les clients
            List<Client> listClient=clientService.readAllByConseiller(conseillerService.readOne(email));
            
            //compter le nombre des comptes pour chaque clients si la liste des clients est different de null
            if(!listClient.isEmpty()){
                
                for (Client client : listClient) {
                    if(!readAllParClient(client.getId()).isEmpty()){
                        cpt+=readAllParClient(client.getId()).size();
                    }
                }
            }
            
        }
        return cpt;

    }

    @Override
    public Epargne create(Epargne t) {

        if (t != null) {
            t.setDateCreation(new Date());
            t.setDateUpdate(new Date());
            t.setEnabled(true);
            return epargneRepository.save(t);
        }
        return null;

    }

    @Override
    public List<Epargne> readAll() {

        return epargneRepository.findByEnabledTrue();

    }

    
    public Epargne readOne(String pk) {

        if (!"".equals(pk)) {
            return epargneRepository.findByNumCptAndEnabledTrue(pk);
        }
        return null;

    }

    @Override
    public Epargne update(Epargne t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            return epargneRepository.save(t);
        }
        return null;

    }

    @Override
    public Boolean delete(Epargne t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            t.setEnabled(false);
            return epargneRepository.save(t) != null;
        }
        return false;

    }
    
}
