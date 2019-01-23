/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.repository;

import ci.proxybanquespring.domaine.Conseiller;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author willi
 */
public interface ConseillerRepository extends JpaRepository<Conseiller, String>{
    
    public Conseiller findByEmailAndPasswordAndEnabledTrue(String email,String password);
    public Conseiller findByEmailAndEnabledTrue(String email);
    public List<Conseiller> findByEnabledTrue();    
    
}
