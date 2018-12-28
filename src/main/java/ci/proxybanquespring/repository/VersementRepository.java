/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.repository;

import ci.proxybanquespring.domaine.Client;
import ci.proxybanquespring.domaine.Compte;
import ci.proxybanquespring.domaine.Versement;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author willi
 */
public interface VersementRepository extends JpaRepository<Versement, Long>{
    
    public Versement findByNumOperationAndEnabledTrue(Long numOp);
    public List<Versement> findByEnabledTrue();
    public List<Versement> findByCompteAndEnabledTrue(Compte compte);
    
}
