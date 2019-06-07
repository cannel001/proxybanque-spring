/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.domaine;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.*;

/**
 *
 * @author Seka Cannel Ulrich Evrard
 *
 * This class represents a user role, it implements serializable interface
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Roles implements Serializable{

    @Id
    @Column(name = "role")
    private String role;

}
