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
 * This class represents a savings, it implements serialzable interface
 *
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@DiscriminatorValue(value = "compte_epargne")
public class Savings extends Account {

    @Column(name = "rate")
    private Float rate;

    /**
     * this method represents the constructor of savings with all parameters
     * @param openDate
     * @param balance
     * @param customer
     */
    public Savings(Date openDate, Double balance, Customer customer) {
        super(openDate, balance, customer);
    }

}
