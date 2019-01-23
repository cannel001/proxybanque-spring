/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.domaine;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author KOUASSI
 */
@Entity(name = "Operation")
@Table(name = "operation")
@Data
@AllArgsConstructor
@Inheritance(strategy =InheritanceType.SINGLE_TABLE)
public abstract class  Operation implements Serializable{
    
    @Id 
    @Column(name = "numOperation")
    private Long numOperation;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateOperation")
    private Date dateOperation;
    
    @Column(name ="montant" )
    private Double montant;
    
    private String note;
    
    private Double ancienSolde;
    
    private Double nouveauSolde;
    
    private String devise;
    
    @Column(name = "agence")
    private String agence;
    
    @Column(name = "caisse")
    private String caisse;
    
    @NotNull
    private Boolean enabled;
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdate;
    
    private Long idUser;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="compte_id")
    private Compte compte;
    
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinTable(name = "Billetages")
    private Set<Billetage> billetages;  
    
    //constructeur
    public Operation(Long numOperation, Date dateOperation, Double montant, String note, Double ancienSolde, Double nouveauSolde, String devise, Long idUser, Compte compte) {
        this.numOperation = numOperation;
        this.dateOperation = dateOperation;
        this.montant = montant;
        this.note = note;
        this.ancienSolde = ancienSolde;
        this.nouveauSolde = nouveauSolde;
        this.devise = devise;
        this.idUser = idUser;
        this.compte = compte;
    }

    public Operation() {
    }
    
    
    
    
}
