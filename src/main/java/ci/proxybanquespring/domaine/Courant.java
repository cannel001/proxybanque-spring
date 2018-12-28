/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.domaine;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Seka Cannel Ulrich Evrard
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue(value = "compte_courant")
public class Courant extends Compte implements Serializable{
    
    @Column(name = "montantInitial")
    private Double montantInitial;
    
    /**
     * 
     * @param montantInitial
     * @param numCpt
     * @param dateOuverture
     * @param solde
     * @param client
     * @param operations 
     */
    

    public Courant(Date dateOuverture, Double solde, Client client) {
        super(dateOuverture, solde, client);
    }

    public Courant(Double montantInitial, Date dateOuverture, Double solde, Client client) {
        super(dateOuverture, solde, client);
        this.montantInitial = montantInitial;
    }

    
    
    
}
