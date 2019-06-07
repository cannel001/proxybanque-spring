/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service;

import ci.proxybanquespring.domaine.Advisor;

import java.util.List;

/**
 *
 * @author willi
 */
public interface IConseillerService {

    public String cryptagePssword(String password);

    public Advisor authentification(String email, String password);

    public Advisor readOneByEmail(String email);

    public void conseillerParDefaut();

    public Advisor create(Advisor t);

    public List<Advisor> readAll();

    public Advisor readOne(String email);

    public Advisor update(Advisor t);

    public Boolean delete(String email);
    
    public String genererCode();

    public String genererPassword();

}
