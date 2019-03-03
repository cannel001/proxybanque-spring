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
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author Seka Cannel Ulrich Evrard
 *
 * Cette classe represente un compte courant, elle herite de la classe Compte
 *
 */
@Entity
@Data
@AllArgsConstructor
@DiscriminatorValue(value = "compte_courant")
public class Courant extends Compte {

    @Column(name = "montantInitial")
    private Double montantInitial;

    /**
     * 
     * @param dateOuverture
     * @param solde
     * @param client 
     */
    public Courant(Date dateOuverture, Double solde, Client client) {
        super(dateOuverture, solde, client);
    }

    public Courant(Double montantInitial, Date dateOuverture, Double solde, Client client) {
        super(dateOuverture, solde, client);
        this.montantInitial = montantInitial;
    }

    public Courant() {
    }

}
