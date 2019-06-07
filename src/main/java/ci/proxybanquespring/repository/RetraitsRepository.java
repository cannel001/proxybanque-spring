/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.repository;

import ci.proxybanquespring.domaine.Customer;
import ci.proxybanquespring.domaine.WithDrawal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author willi
 */
public interface RetraitsRepository extends JpaRepository<WithDrawal, Long>{
    
    public WithDrawal findByNumOperationAndEnabledTrue(Long numOperation);
    
    public List<WithDrawal> findByEnabledTrue();
    
    public List<WithDrawal> findByCompteClientAndEnabledTrue(Customer c);
    
}
