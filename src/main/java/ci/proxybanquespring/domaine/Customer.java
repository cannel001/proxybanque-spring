/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.domaine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;

/**
 *
 * @author Seka Cannel Ulrich Evrard
 *
 * this class represents an customer, it implemnts the serializable interface
 *
 */
@Entity
@Table(name = "customer")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 15)
    private String name;

    @Column(name = "firstName", nullable = false, length = 25)
    private String firstName;

    @Column(name = "number", nullable = false, length = 14)
    private String number;

    @Column(name = "email")
    private String email;

    @Column(name = "adress", nullable = false, length = 25)
    private String adress;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conseiller_id")
    private Advisor advisor;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL)
    private Collection<Account> accounts;

    /**
     * this method represents a constructor of customer, it implements serializable interface
     * @param name
     * @param firstName
     * @param number
     * @param email
     * @param advisor
     * @param adress
     */
    public Customer(String name, String firstName, String number, String email, Advisor advisor, String adress) {
        this.name = name;
        this.firstName = firstName;
        this.number = number;
        this.email = email;
        this.advisor = advisor;
        this.adress = adress;
    }

}
