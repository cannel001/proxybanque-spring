/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service.impl;

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
    
    @Override
     public Billetage create(Billetage t) {

        if (t != null) {
            t.setDateCreation(new Date());
            t.setDateUpdate(new Date());
            t.setEnabled(true);
            return billetageRepository.save(t);
        }
        return null;

    }

     @Override
    public List<Billetage> readAll() {
        return billetageRepository.findByEnabledTrue();
    }

    @Override
    public Billetage readOne(Long pk) {

        if (pk > 0) {
            return billetageRepository.findByIdAndEnabledTrue(pk);
        }
        return null;

    }

    @Override
    public Billetage update(Billetage t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            return billetageRepository.save(t);
        }
        return null;

    }

    @Override
    public Boolean delete(Billetage t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            t.setEnabled(false);
            return billetageRepository.save(t) != null;
        }
        return false;

    }
    
}
