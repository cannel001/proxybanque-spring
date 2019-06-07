/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.repository;

import ci.proxybanquespring.domaine.Customer;
import ci.proxybanquespring.domaine.Account;
import ci.proxybanquespring.domaine.Payment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author willi
 */
public interface VersementRepository extends JpaRepository<Payment, Long>{
    
    public List<Payment> findByCompteClientAndEnabledTrue(Customer c);
    public Payment findByNumOperationAndEnabledTrue(Long numOp);
    public List<Payment> findByEnabledTrue();
    public List<Payment> findByCompteAndEnabledTrue(Account account);
    
}
