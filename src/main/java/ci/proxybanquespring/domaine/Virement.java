/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.domaine;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author KOUASSI
 */
@Entity
@Data
@DiscriminatorValue(value="operation_virement")
public class Virement extends Operation implements Serializable {
    
    @Column(name = "typvirement")
    private String typVirement;
    
    @Column(name = "reference")
    private Long reference;
  
    //constructeur
    public Virement(String typVirement, Long reference, Long numOperation, Date dateOperation, Double montant, String note, Double ancienSolde, Double nouveauSolde, String devise, Long idUser, Compte compte) {
        super(numOperation, dateOperation, montant, note, ancienSolde, nouveauSolde, devise, idUser, compte);
        this.typVirement = typVirement;
        this.reference = reference;
    }
    
    public Virement(){
        
    }
    
    
    
}
