/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.domaine;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Data;

/**
 *
 * @author KOUASSI
 */
@Entity
@Data
@DiscriminatorValue(value = "operation_versement")
public class Versement extends Operation implements Serializable {

    private String remettant;

    private String telRemettant;

    private Integer timbre;

    //constructeur
    public Versement(String agence, String caisse, String remettant, String telRemettant, Integer timbre, Long numOperation, Date dateOperation, Double montant, String note, Double ancienSolde, Double nouveauSolde, String devise, Long idUser, Compte compte) {
        super(numOperation, dateOperation, montant, note, ancienSolde, nouveauSolde, devise, idUser, compte);
        this.remettant = remettant;
        this.telRemettant = telRemettant;
        this.timbre = timbre;
    }

    public Versement() {
    }

}
