/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service.impl;

import ci.proxybanquespring.domaine.Retraits;
import ci.proxybanquespring.repository.RetraitsRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author willi
 */
@Service
public class RetraitsService {
    
    //les proprietes
    @Autowired
    private RetraitsRepository repository;
    
     public Retraits create(Retraits t) {

        if (t != null) {
            t.setDateCreation(new Date());
            t.setDateUpdate(new Date());
            t.setEnabled(true);
            return repository.save(t);
        }
        return null;

    }

    public List<Retraits> readAll() {

        return repository.findByEnabledTrue();

    }

    public Retraits readOne(Long numOperation) {

        if (numOperation > 0) {
            return repository.findByNumOperationAndEnabledTrue(numOperation);
        }
        return null;

    }

    
    public Retraits update(Retraits t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            return repository.save(t);
        }
        return null;

    }

    
    public Boolean delete(Retraits t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            t.setEnabled(false);
            return repository.save(t) != null;
        }
        return false;

    }
    
}
