/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.domaine;

import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.NoArgsConstructor;

/**
 *
 * @author Seka Cannel Ulrich Evrard
 *
 * this class represents a withdrawal, it implements serializable interface
 */
@Entity
@NoArgsConstructor
@DiscriminatorValue(value = "withdrawal_operation")
public class WithDrawal extends Operation {

    /**
     * this method represents a constructor of withdrawal with all parameters
     * @param cashRegister
     * @param agency
     * @param numOperation
     * @param operationDate
     * @param amount
     * @param note
     * @param oldBalance
     * @param newBalance
     * @param currency
     * @param account
     */
    public WithDrawal(String cashRegister, String agency, Long numOperation, Date operationDate, Double amount, String note, Double oldBalance, Double newBalance, String currency, Account account) {
        super(numOperation, operationDate, amount, note, oldBalance, newBalance, currency, account);
    }

}
