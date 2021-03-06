/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.repository;

import ci.proxybanquespring.domaine.Customer;
import ci.proxybanquespring.domaine.Current;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author willi
 */
public interface CourantRepository extends JpaRepository<Current, String>{
    
    public Current findByNumCptAndEnabledTrue(String numCpt);
    public List<Current> findByClientAndEnabledTrue(Customer client);
    public List<Current> findByEnabledTrue();
    
}
