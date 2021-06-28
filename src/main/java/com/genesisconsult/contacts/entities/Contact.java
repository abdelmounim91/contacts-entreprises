package com.genesisconsult.contacts.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * Contact class has two subclasses Employee and Freelance
 */

/** Informations are stored in one table*/
@NamedNativeQuery(
        name = "listEntreprises",
        query = "select e.* from entreprise e inner join  entreprise_contact ec on e.id = ec.entreprise_id inner join contact c on ec.contact_id = c.id where c.id =:id",
        resultClass = Entreprise.class)
@Entity
@Table(name = "contact")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "contact_type",
        discriminatorType = DiscriminatorType.STRING)
public class Contact implements Serializable {
    /**Unique and Autoincrement*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**Contact must have address, lastname and firstname*/
    @NotNull
    @Size(min = 4)
    private String address;
    @NotNull
    @Size(min = 2)
    private String nom;
    @NotNull
    @Size(min = 2)
    private String prenom;

    @Column(name = "contact_type", insertable = false, updatable = false)
    private String type;

    @JsonIgnore
    @ManyToMany(mappedBy = "contacts", fetch = FetchType.LAZY)
    private List<Entreprise> entreprises = new ArrayList<Entreprise>();

    public Contact() {
    }

    public Contact(Long id, String address, String nom, String prenom) {
        this.id = id;
        this.address = address;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getType() {
        return type;
    }

    public List<Entreprise> getEntreprises() {
        return entreprises;
    }

    public void setEntreprises(List<Entreprise> entreprises) {
        this.entreprises = entreprises;
    }

    public void addEntreprise(Entreprise entreprise) {

        if (this.entreprises == null) {
            this.entreprises = new ArrayList<Entreprise>();
        }
        this.entreprises.add(entreprise);
        /**To add elements in two directions*/
        entreprise.getContacts().add(this);
    }

    public void removeContactFromEntreprises() {
        for (Entreprise entreprise : this.entreprises) {
            entreprise.getContacts().remove(this);
        }
    }
}
