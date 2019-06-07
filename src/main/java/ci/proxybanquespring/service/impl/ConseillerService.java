/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service.impl;

import ci.proxybanquespring.domaine.Advisor;
import ci.proxybanquespring.domaine.Roles;
import ci.proxybanquespring.repository.ConseillerRepository;
import ci.proxybanquespring.service.IConseillerService;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

        String codeCrypte = (new BCryptPasswordEncoder()).encode(password);
        return codeCrypte;

    }

    @Override
    public Advisor authentification(String email, String password) {

        if (!"".equals(email) && !"".equals(password)) {
            return conseillerRepository.findByEmailAndPasswordAndEnabledTrue(email, password);
        }
        return null;

    }

    @Override
    public Advisor readOneByEmail(String email) {

        if (!"".equals(email)) {
            return conseillerRepository.findByEmailAndEnabledTrue(email);
        }
        return null;

    }

    @Override
    public Advisor create(Advisor t) {

        if (t != null) {
            t.setDateCreation(new Date());
            t.setDateUpdate(new Date());
            t.setEnabled(true);
            return conseillerRepository.save(t);
        }
        return null;

    }

    @Override
    public List<Advisor> readAll() {

        return conseillerRepository.findByEnabledTrue();

    }

    @Override
    public Advisor readOne(String email) {

        if (!"".equals(email)) {
            return conseillerRepository.findByEmailAndEnabledTrue(email);
        }
        return null;

    }

    @Override
    public Advisor update(Advisor t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            return conseillerRepository.save(t);
        }
        return null;

    }

    @Override
    public Boolean delete(String email) {
        
        Advisor t=readOne(email);

        if (t != null) {
            t.setDateUpdate(new Date());
            t.setEnabled(false);
            return conseillerRepository.save(t) != null;
        }
        return null;

    }

    @Override
    public void conseillerParDefaut() {

        System.out.println("************** passage dans advisor par defaut *****************");

        if (conseillerRepository.findByEnabledTrue().isEmpty()) {

            Set<Roles> mesRoles = new HashSet<>();
            Set<Roles> mesRoles2 = new HashSet<>();

            mesRoles.add(new Roles("ADMIN"));
            mesRoles2.add(new Roles("USER"));

            System.out.println("************** creation du nouveau advisor *****************");
            Advisor advisor = new Advisor();
            Advisor advisor2 = new Advisor();

            advisor.setNom("Root");
            advisor.setEmail("root@root.com");
            advisor.setPassword((new BCryptPasswordEncoder()).encode("proxybanque"));
            advisor.setRoleses(mesRoles);

            advisor2.setNom("user");
            advisor2.setEmail("user@user.com");
            advisor2.setPassword((new BCryptPasswordEncoder()).encode("proxybanque"));
            advisor2.setRoleses(mesRoles2);

            this.create(advisor);
            this.create(advisor2);

            System.out.println("************** nouveau advisor cree *****************");
        }

    }

    @Override
    public String genererCode() {

        Random aleatoire = new Random();
        String code = "0123456789";
        String codeGenere = "";
        int index;

        for (int i = 0; i < 6; i++) {
            index = aleatoire.nextInt(code.length());
            codeGenere += code.charAt(index);
        }

        return codeGenere;

    }

    @Override
    public String genererPassword() {
        return genererCode();
    }

}
