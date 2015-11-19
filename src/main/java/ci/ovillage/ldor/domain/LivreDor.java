package ci.ovillage.ldor.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A LivreDor.
 */
@Entity
@Table(name = "livre_dor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "livredor")
public class LivreDor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "message", nullable = false)
    private String message;

    @NotNull
    @Column(name = "nom_visiteur", nullable = false)
    private String nomVisiteur;

    @NotNull
    @Column(name = "prenom_visiteur", nullable = false)
    private String prenomVisiteur;

    @NotNull
    @Column(name = "contact", nullable = false)
    private String contact;

    @Column(name = "twitter")
    private String twitter;

    @Column(name = "mail")
    private String mail;

    @NotNull
    @Column(name = "date_visite", nullable = false)
    private LocalDate dateVisite;

    @ManyToOne
    private User dignitaire;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNomVisiteur() {
        return nomVisiteur;
    }

    public void setNomVisiteur(String nomVisiteur) {
        this.nomVisiteur = nomVisiteur;
    }

    public String getPrenomVisiteur() {
        return prenomVisiteur;
    }

    public void setPrenomVisiteur(String prenomVisiteur) {
        this.prenomVisiteur = prenomVisiteur;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public LocalDate getDateVisite() {
        return dateVisite;
    }

    public void setDateVisite(LocalDate dateVisite) {
        this.dateVisite = dateVisite;
    }

    public User getDignitaire() {
        return dignitaire;
    }

    public void setDignitaire(User user) {
        this.dignitaire = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LivreDor livreDor = (LivreDor) o;

        if ( ! Objects.equals(id, livreDor.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LivreDor{" +
            "id=" + id +
            ", message='" + message + "'" +
            ", nomVisiteur='" + nomVisiteur + "'" +
            ", prenomVisiteur='" + prenomVisiteur + "'" +
            ", contact='" + contact + "'" +
            ", twitter='" + twitter + "'" +
            ", mail='" + mail + "'" +
            ", dateVisite='" + dateVisite + "'" +
            '}';
    }
}
