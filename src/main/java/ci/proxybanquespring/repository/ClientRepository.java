/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.repository;

import ci.proxybanquespring.domaine.Client;
import ci.proxybanquespring.domaine.Conseiller;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author willi
 */
public interface ClientRepository extends JpaRepository<Client, Long>{
    
    public List<Client> findByConseillerAndEnabledTrue(Conseiller conseiller);
    public Client findByIdAndEnabledTrue(Long id);
    public List<Client> findByEnabledTrue();
    
}
