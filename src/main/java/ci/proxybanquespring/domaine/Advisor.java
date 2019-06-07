/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.domaine;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

/**
 *
 * @author Seka Cannel Ulrich Evrard
 *
 * this class represents an advisor, it implements serializable interface
 *
 */
@Entity
@Table(name = "advisor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Advisor implements Serializable {

    @Id
    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "number")
    private String number;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Boolean enabled;

    @CreatedBy
    @Column(name = "createdBy", updatable = false, length = 20)
    @JsonIgnore
    private String createdBy;

    @CreatedDate
    @Column(name = "createdDate", updatable = false)
    @JsonIgnore
    private Instant createdDate;

    @LastModifiedBy
    @Column(name = "lastModfiedBy", length = 20)
    @JsonIgnore
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "lastModifiedDate")
    @JsonIgnore
    private Instant lastModifiedDate;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinTable(name = "advisor_roles", joinColumns = @JoinColumn(name = "email"), inverseJoinColumns = @JoinColumn(name = "role"))
    private Set<Roles> roleses;

    /**
     * this method represents the constructor of advisor with all parameters
     * @param name
     * @param firstName
     * @param number
     * @param email
     * @param password
     */
    public Advisor(String name, String firstName, String number, String email, String password) {
        this.name = name;
        this.firstName = firstName;
        this.number = number;
        this.email = email;
        this.password = password;
    }

}
