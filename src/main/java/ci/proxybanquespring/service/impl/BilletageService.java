/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service.impl;

import ci.proxybanquespring.domaine.Billetage;
import ci.proxybanquespring.domaine.Client;
import ci.proxybanquespring.repository.BilletageRepository;
import ci.proxybanquespring.service.IBilletageService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author willi
 */
@Service
public class BilletageService implements IBilletageService{
    
    //les proprietes
    @Autowired
    private BilletageRepository billetageRepository;
    
     public Billetage create(Billetage t) {

        if (t != null) {
            t.setDateCreation(new Date());
            t.setDateUpdate(new Date());
            t.setEnabled(true);
            return billetageRepository.save(t);
        }
        return null;

    }

    public List<Billetage> readAll() {
        return billetageRepository.findByEnabledTrue();
    }

    public Billetage readOne(Long pk) {

        if (pk > 0) {
            return billetageRepository.findByIdAndEnabledTrue(pk);
        }
        return null;

    }

    
    public Billetage update(Billetage t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            return billetageRepository.save(t);
        }
        return null;

    }

    
    public Boolean delete(Billetage t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            t.setEnabled(false);
            return billetageRepository.save(t) != null;
        }
        return false;

    }
    
}
