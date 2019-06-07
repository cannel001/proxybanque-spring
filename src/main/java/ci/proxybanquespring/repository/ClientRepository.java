/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.repository;

import ci.proxybanquespring.domaine.Advisor;
import ci.proxybanquespring.domaine.Customer;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author willi
 */
public interface ClientRepository extends JpaRepository<Customer, Long>{
    
    public List<Customer> findByConseillerAndEnabledTrue(Advisor advisor);
    public Customer findByIdAndEnabledTrue(Long id);
    public List<Customer> findByEnabledTrue();
    
}
