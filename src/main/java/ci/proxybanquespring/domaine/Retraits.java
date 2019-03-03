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
 * Cette classe represente un retrait, elle herite de la classe Operation
 */
@Entity
@NoArgsConstructor
@DiscriminatorValue(value = "operation_retrait")
public class Retraits extends Operation {

    public Retraits(String caisse, String agence, Long numOperation, Date dateOperation, Double montant, String note, Double ancienSolde, Double nouveauSolde, String devise, Long idUser, Compte compte) {
        super(numOperation, dateOperation, montant, note, ancienSolde, nouveauSolde, devise, idUser, compte);
    }

}
