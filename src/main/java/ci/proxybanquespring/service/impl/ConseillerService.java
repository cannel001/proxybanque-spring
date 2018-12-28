/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service.impl;

import ci.proxybanquespring.domaine.Conseiller;
import ci.proxybanquespring.repository.ConseillerRepository;
import ci.proxybanquespring.service.IConseillerService;
import com.google.common.hash.Hashing;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author willi
 */
@Service
public class ConseillerService implements IConseillerService {

    //les proprietes
    @Autowired
    private ConseillerRepository conseillerRepository;

    @Override
    public String cryptagePssword(String password) {

        String codeCrypte = Hashing.sha256().hashString(password, Charset.defaultCharset()).toString();
        return codeCrypte;

    }

    @Override
    public Conseiller authentification(String email, String password) {

        if (!"".equals(email) && !"".equals(password)) {
            return conseillerRepository.findByEmailAndPasswordAndEnabledTrue(email, password);
        }
        return null;

    }

    @Override
    public Conseiller readOneByEmail(String email) {

        if (!"".equals(email)) {
            return conseillerRepository.findByEmailAndEnabledTrue(email);
        }
        return null;

    }

    
    public Conseiller create(Conseiller t) {

        if (t != null) {
            t.setDateCreation(new Date());
            t.setDateUpdate(new Date());
            t.setEnabled(true);
            return conseillerRepository.save(t);
        }
        return null;

    }

    
    public List<Conseiller> readAll() {

        return conseillerRepository.findByEnabledTrue();

    }

    
    public Conseiller readOne(Long pk) {

        if (pk > 0) {
            return conseillerRepository.findByIdAndEnabledTrue(pk);
        }
        return null;

    }

    
    public Conseiller update(Conseiller t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            return conseillerRepository.save(t);
        }
        return null;

    }

    
    public Boolean delete(Conseiller t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            t.setEnabled(false);
            return conseillerRepository.save(t) != null;
        }
        return null;

    }

    
    public void conseillerParDefaut() {
        
        System.out.println("************** passage dans conseiller par defaut *****************");

        if(conseillerRepository.findByEnabledTrue().isEmpty()){
            System.out.println("************** creation du nouveau conseiller *****************");
            Conseiller conseiller=new Conseiller();
            conseiller.setNom("Root");
            conseiller.setEmail("root@root.com");
            this.create(conseiller);
            
            System.out.println("************** nouveau conseiller cree *****************");
        }

    }

}
