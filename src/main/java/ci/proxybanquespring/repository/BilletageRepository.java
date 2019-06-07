/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author willi
 */
public interface BilletageRepository extends JpaRepository<Billetage, Long>{
    
    public Billetage findByIdAndEnabledTrue(Long id);
    
    public List<Billetage> findByEnabledTrue();
    
}
