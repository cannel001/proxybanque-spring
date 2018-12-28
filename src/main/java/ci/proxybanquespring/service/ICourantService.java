/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service;

import ci.proxybanquespring.domaine.Courant;
import java.util.List;

/**
 *
 * @author willi
 */
public interface ICourantService{
    
    public String generateNumeroCompte();
    
    public Boolean verifNumeroCompte(String numero);
    
    public List<Courant> readAllParClient(Long idClient);
    
    public int countByConseiller(Long idConseiller);
    
}
