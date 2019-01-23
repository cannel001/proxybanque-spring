/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.repository;

import ci.proxybanquespring.domaine.Client;
import ci.proxybanquespring.domaine.Conseiller;
import ci.proxybanquespring.domaine.Epargne;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author willi
 */
public interface EpargneRepository extends JpaRepository<Epargne, String>{
    
    public Epargne findByNumCptAndEnabledTrue(String numCpt);
    public List<Epargne> findByClientAndEnabledTrue(Client client);
    public List<Epargne> findByEnabledTrue();
    
    
}
