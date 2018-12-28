/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service.impl;

import ci.proxybanquespring.domaine.Client;
import ci.proxybanquespring.domaine.Courant;
import ci.proxybanquespring.repository.CourantRepository;
import ci.proxybanquespring.service.ICourantService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author willi
 */
@Service
public class CourantService implements ICourantService {

    //les proprietes
    @Autowired
    private CourantRepository courantRepository;

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
            return courantRepository.findByNumCptAndEnabledTrue(numero) != null;
        }
        return false;

    }

    @Override
    public List<Courant> readAllParClient(Long idClient) {

        if (idClient > 0) {
            Client client = clientService.readOne(idClient);
            return courantRepository.findByClientAndEnabledTrue(client);
        }
        return null;

    }

    @Override
    public int countByConseiller(Long idConseiller) {
        
        int cpt=0;

        if (idConseiller > 0) {
            
            //recuperation de la liste de tous les clients
            List<Client> listClient=clientService.readAllByConseiller(conseillerService.readOne(idConseiller));
            
            //compter le nombre des comptes pour chaque clients si la liste des clients est different de null
            if(listClient!=null){
                
                for (Client client : listClient) {
                    if(readAllParClient(client.getId())!=null){
                        cpt+=readAllParClient(client.getId()).size();
                    }
                }
            }
            
        }
        return cpt;

    }

    
    public Courant create(Courant t) {

        if (t != null) {
            t.setDateCreation(new Date());
            t.setDateUpdate(new Date());
            t.setEnabled(true);
            return courantRepository.save(t);
        }
        return null;

    }

    
    public List<Courant> readAll() {

        return courantRepository.findByEnabledTrue();

    }

    
    public Courant readOne(String pk) {

        if (!"".equals(pk)) {
            return courantRepository.findByNumCptAndEnabledTrue(pk);
        }
        return null;

    }

    
    public Courant update(Courant t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            return courantRepository.save(t);
        }
        return null;

    }

    
    public Boolean delete(Courant t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            t.setEnabled(false);
            return courantRepository.save(t) != null;
        }
        return false;

    }

}
