/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.domaine;

import java.util.Date;
import javax.persistence.Entity;
import lombok.NoArgsConstructor;

/**
 *
 * @author willi
 */
@Entity
@NoArgsConstructor
public class Retraits extends Operation{
    
    private String caisse;
    
    private String agence;

    public Retraits(String caisse, String agence, Long numOperation, Date dateOperation, Double montant, String note, Double ancienSolde, Double nouveauSolde, String devise, Long idUser, Compte compte) {
        super(numOperation, dateOperation, montant, note, ancienSolde, nouveauSolde, devise, idUser, compte);
        this.caisse = caisse;
        this.agence = agence;
    }
    
    
    
}
