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
 * This class represents a payment, it implements serializable interface
 */
@Entity
@Getter
@Setter
@ToString
@DiscriminatorValue(value = "payment_operation")
public class Payment extends Operation {

    @Column(name = "remitter")
    private String remitter;

    @Column(name = "telRemitter")
    private String telRemitter;

    @Column(name = "stamp")
    private Integer stamp;

    /**
     * this method represents the constructor of Payment with all parameters
     * @param remitter
     * @param telRemitter
     * @param stamp
     * @param numOperation
     * @param operationDate
     * @param amount
     * @param note
     * @param oldBalance
     * @param newBalance
     * @param currency
     * @param account
     */
    public Payment(String remitter, String telRemitter, Integer stamp, Long numOperation, Date operationDate, Double amount, String note, Double oldBalance, Double newBalance, String currency, Account account) {
        super(numOperation, operationDate, amount, note, oldBalance, newBalance, currency, account);
        this.remitter = remitter;
        this.telRemitter = telRemitter;
        this.stamp = stamp;
    }

    public Payment() {
    }

}
