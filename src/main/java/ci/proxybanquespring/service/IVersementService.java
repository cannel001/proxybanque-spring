/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service;

import ci.proxybanquespring.domaine.Versement;
import java.util.List;

/**
 *
 * @author willi
 */
public interface IVersementService{
    
    public Long generateNumTransc();
    
    public List<Versement> readAllVersementPrClient(Long idClient);
    
    public String typeCompte(String numeroCpt);
    
    public List<Versement> readAllVersementParCompte(String compte);
    
}
