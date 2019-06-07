/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.domaine;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Seka Cannel Ulrich Evrard
 *
 * This class represents a transfer, it implements serializable interface
 */
@Entity
@Getter
@Setter
@ToString
@DiscriminatorValue(value = "transfer_operation")
public class Transfer extends Operation {

    @Column(name = "typTransfer")
    private String typTransfer;

    @Column(name = "reference")
    private Long reference;

    /**
     * This method represents the constructor of transfer with all parameters
     * @param typTransfer
     * @param reference
     * @param numOperation
     * @param operationDate
     * @param amount
     * @param note
     * @param oldBalance
     * @param newBalance
     * @param currency
     * @param account
     */
    public Transfer(String typTransfer, Long reference, Long numOperation, Date operationDate, Double amount, String note, Double oldBalance, Double newBalance, String currency, Account account) {
        super(numOperation, operationDate, amount, note, oldBalance, newBalance, currency, account);
        this.typTransfer = typTransfer;
        this.reference = reference;
    }

    public Transfer() {

    }

}
