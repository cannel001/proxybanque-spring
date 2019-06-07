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

import lombok.*;

/**
 *
 * @author Seka Cannel Ulrich Evrard
 *
 * this class represents a current account, it implements serializable interface
 *
 */
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@DiscriminatorValue(value = "current_account")
public class Current extends Account {

    @Column(name = "initialAmount",nullable = false)
    private Double initialAmount;


    public Current(Date openDate, Double balance, Customer customer) {
        super(openDate, balance, customer);
    }

    /**
     * this method represents the constructor of current_account with all parameters
     * @param initialAmount
     * @param openDate
     * @param balance
     * @param customer
     */
    public Current(Double initialAmount, Date openDate, Double balance, Customer customer) {
        super(openDate, balance, customer);
        this.initialAmount = initialAmount;
    }

    public Current() {
    }

}
