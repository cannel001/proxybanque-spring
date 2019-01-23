/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.repository;

import ci.proxybanquespring.domaine.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author willi
 */
public interface OperationRepository extends JpaRepository<Operation, Long>{
    
    public Object findByNumOperationAndEnabledTrue(Long numOperation);
    
}
