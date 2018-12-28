/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.repository;

import ci.proxybanquespring.domaine.Client;
import ci.proxybanquespring.domaine.Conseiller;
import ci.proxybanquespring.domaine.Courant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author willi
 */
public interface CourantRepository extends JpaRepository<Courant, String>{
    
    public Courant findByNumCptAndEnabledTrue(String numCpt);
    public List<Courant> findByClientAndEnabledTrue(Client client);
    public List<Courant> findByEnabledTrue();
    
}
