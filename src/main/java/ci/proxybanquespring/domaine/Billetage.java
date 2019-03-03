/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.domaine;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author seka Cannel Ulrich Evrard
 *
 * cette classe represente un billetage, elle implemente l'interface
 * Serializable
 *
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Billetage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String valeurRecu;

    private Integer nbRecu;

    private Long montantRecu;

    private Integer nbRendu;

    private Long montantRendu;

    private Long idUser;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdate;

    private Boolean enabled;

}
