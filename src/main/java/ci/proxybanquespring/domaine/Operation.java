/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.domaine;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

/**
 *
 * @author Seka Cannel Ulrich Evrard
 *
 * This class represents an operation, it implements serializable interface
 *
 */
@Entity
@Table(name = "operation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Operation implements Serializable {

    @Id
    @Column(name = "numOperation")
    private Long numOperation;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "operationDate")
    private Date operationDate;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "note")
    private String note;

    @Column(name = "oldBalance")
    private Double oldBalance;

    @Column(name = "newBalance")
    private Double newBalance;

    @Column(name = "currency")
    private String currency;

    @Column(name = "agency")
    private String agency;

    @Column(name = "cashRegister")
    private String cashRegister;

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
    @JoinColumn(name = "compte_id")
    private Account account;

    /**
     * this method represents the constructor of operation with all parameters
     * @param numOperation
     * @param operationDate
     * @param amount
     * @param note
     * @param oldBalance
     * @param newBalance
     * @param currency
     * @param account
     */
    public Operation(Long numOperation, Date operationDate, Double amount, String note, Double oldBalance, Double newBalance, String currency, Account account) {
        this.numOperation = numOperation;
        this.operationDate = operationDate;
        this.amount = amount;
        this.note = note;
        this.oldBalance = oldBalance;
        this.newBalance = newBalance;
        this.currency = currency;
        this.account = account;
    }
}
