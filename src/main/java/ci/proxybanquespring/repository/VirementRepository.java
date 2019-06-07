/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.repository;

import ci.proxybanquespring.domaine.Customer;
import ci.proxybanquespring.domaine.Transfer;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author willi
 */
public interface VirementRepository extends JpaRepository<Transfer, Long>{
    
    public Transfer findByNumOperationAndEnabledTrue(Long numOperation);
    public List<Transfer> findByEnabledTrue();
    public Transfer findByReferenceAndTypVirementAndEnabledTrue(Long reference, String typVirement);
    public List<Transfer> findByCompteClientAndEnabledTrue(Customer c);
    public List<Transfer> findByCompteClientAndEnabledTrueAndTypVirement(Customer c, String typVirement);
    
}
