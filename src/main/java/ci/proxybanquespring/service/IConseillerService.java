/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service;

import ci.proxybanquespring.domaine.Conseiller;

/**
 *
 * @author willi
 */
public interface IConseillerService{
    
    public String cryptagePssword(String password);
    
    public Conseiller authentification(String email,String password);
    
    public Conseiller readOneByEmail(String email);
    
    public void conseillerParDefaut();
    
}
