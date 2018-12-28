/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.repository;

import ci.proxybanquespring.domaine.Virement;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author willi
 */
public interface VirementRepository extends JpaRepository<Virement, Long>{
    
    public Virement findByNumOperationAndEnabledTrue(Long numOperation);
    public List<Virement> findByEnabledTrue();
    public Virement findByReferenceAndTypVirementAndEnabledTrue(Long reference,String typVirement);
    
}
