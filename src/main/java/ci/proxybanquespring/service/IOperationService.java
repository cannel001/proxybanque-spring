/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service;

/**
 *
 * @author willi
 */
public interface IOperationService {
    
    public Long generateNumTransc();
    
    public String typeCompte(String numeroCpt);
    
    public Boolean verifSolde(String cpt, Double montant);
        
}
