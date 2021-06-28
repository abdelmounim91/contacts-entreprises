package com.genesisconsult.contacts.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Entreprise class
 */
@NamedNativeQuery(
        name = "listContact",
        query = "select  c.* from contact c inner join  entreprise_contact ec on c.id = ec.contact_id inner join entreprise e on ec.entreprise_id = e.id where e.id = :id",
        resultClass = Contact.class)
@Entity
@Table(name = "entreprise")
public class Entreprise implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Entreprise must have address
     */
    @NotNull
    @Size(min = 4)
    private String address;

    /**
     * Must have a unique numTVA and exactly 9 digits
     */
    @NotNull
    @Column(length = 9, unique = true)
    @Size(min = 9, max = 9)
    private String numTVA;

    /**
     * Join Table entreprise_contact between Entreprise and Contact
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "entreprise_contact",
            joinColumns = {
                    @JoinColumn(name = "entreprise_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "contact_id", referencedColumnName = "id")
            })
    private List<Contact> contacts = new ArrayList<Contact>();

    public Entreprise() {
    }

    public Entreprise(Long id, String address, String numTVA) {
        this.id = id;
        this.address = address;
        this.numTVA = numTVA;
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

    public String getNumTVA() {
        return numTVA;
    }

    public void setNumTVA(String numTVA) {
        this.numTVA = numTVA;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public void addContact(Contact contact) {
        if (this.contacts == null) {
            this.contacts = new ArrayList<Contact>();
        }
        this.contacts.add(contact);
        /**To add elements in two directions*/
        contact.getEntreprises().add(this);
    }

    public void removeEntrepriseFromContacts() {
        for (Contact contact : this.contacts) {
            contact.getEntreprises().remove(this);
        }
    }
}
