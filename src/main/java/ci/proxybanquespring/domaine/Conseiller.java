/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.domaine;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Seka Cannel Ulrich Evrard
 */
@Entity(name = "Conseiller")
@Table(name = "conseiller")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conseiller implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name ="nom" )
    private String nom;
    
    @Column(name = "prenom")
    private String prenom;
    
    @Column(name = "tel")
    private String tel;
    
    @Column(name = "email")
    private String email;
    
    @Column(name ="password" )
    private String password;
    
    @Column(name = "roleUser")
    private String roleUser;
    
    @NotNull
    private Boolean enabled;
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdate;
    
    @OneToMany(fetch = FetchType.LAZY,mappedBy ="conseiller",cascade = CascadeType.MERGE )
    private Collection<Client> clients;
    
    /**
     * Constructeur sans id
     * @param nom
     * @param prenom
     * @param tel
     * @param email
     * @param password
     * @param role 
     */
    public Conseiller(String nom, String prenom, String tel, String email, String password,String role) {
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.password = password;
        this.roleUser=role;
    }
    
    
    
    
}