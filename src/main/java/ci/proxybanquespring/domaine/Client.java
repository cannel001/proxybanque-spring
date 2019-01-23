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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
 * @author Seka Cannel Ulrich Evrard
 */
@Entity(name = "Client")
@Table(name = "client")
@Data
@AllArgsConstructor
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "tel")
    private String tel;

    @Column(name = "email")
    private String email;

    @Column(name = "adresse")
    private String adresse;

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
    @JoinColumn(name = "conseiller_id")
    private Conseiller conseiller;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL)
    private Collection<Compte> comptes;

    /**
     * Constructeur sans id
     *
     * @param nom
     * @param prenom
     * @param tel
     * @param email
     * @param conseiller
     * @param adresse
     */
    public Client(String nom, String prenom, String tel, String email, Conseiller conseiller, String adresse) {
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.conseiller = conseiller;
        this.adresse = adresse;
    }

    public Client() {
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", tel=" + tel + ", email=" + email + ", adresse=" + adresse + ", enabled=" + enabled + ", dateCreation=" + dateCreation + ", dateUpdate=" + dateUpdate + ", idUser=" + idUser + '}';
    }

}
