/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.domaine;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author willi
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Roles {
    
    @Id
    @Column(name = "role")
    private String role;
    
}
