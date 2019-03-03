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
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Seka Cannel Ulrich Evrard
 *
 * Cette classe represente un epargne, elle herite de la classe Compte
 *
 */
@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue(value = "compte_epargne")
public class Epargne extends Compte {

    @Column(name = "taux")
    private Float taux;

    public Epargne(Date dateOuverture, Double solde, Client client) {
        super(dateOuverture, solde, client);
    }

}
