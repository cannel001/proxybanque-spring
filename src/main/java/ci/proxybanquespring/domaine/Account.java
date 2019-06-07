/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.domaine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author Seka Cannel Ulrich Evrard
 *
 * this class represents an account, it implements serializable interface
 *
 */
@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "typ_cpt")
public abstract class Account implements Serializable {

    @Id
    @Column(name = "numCpt")
    private String numCpt;

    @Temporal(TemporalType.DATE)
    @Column(name = "openDate")
    @DateTimeFormat(pattern = "yyyy-MM-d")
    private Date openDate;

    @Column(name = "balance", nullable = false)
    private Double balance;

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
    private Customer customer;

    /**
     * this method represents the constructor of account with all parameters
     * @param openDate
     * @param balance
     * @param customer
     */
    public Account(Date openDate, Double balance, Customer customer) {
        this.openDate = openDate;
        this.balance = balance;
        this.customer = customer;
    }

}
