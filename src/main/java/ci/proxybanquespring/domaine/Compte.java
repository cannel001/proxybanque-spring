/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.domaine;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Seka Cannel Ulrich Evrard
 *
 * Cette classe represente un compte, elle implemente l'interface serializable
 *
 */
@Entity(name = "Compte")
@Table(name = "compte")
@Data
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_cpt")
public abstract class Compte implements Serializable {

    @Id
    @Column(name = "numCpt")
    private String numCpt;

    @Temporal(TemporalType.DATE)
    @Column(name = "dateOuverture")
    @DateTimeFormat(pattern = "yyyy-MM-d")
    private Date dateOuverture;

    private Double solde;

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
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "compte", cascade = CascadeType.ALL)
    private Collection<Operation> operations;

    /**
     * Constructeur sans id
     *
     * @param dateOuverture
     * @param solde
     * @param client
     */
    public Compte(Date dateOuverture, Double solde, Client client) {
        this.dateOuverture = dateOuverture;
        this.solde = solde;
        this.client = client;
    }

    public Compte() {
    }

}
