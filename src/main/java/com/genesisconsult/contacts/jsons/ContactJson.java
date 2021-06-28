package com.genesisconsult.contacts.jsons;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Json used for adding or modify a Contact
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ContactJson {

    private Long id;
    private String address;
    private String nom;
    private String prenom;
    private String type;
    private String numTVA;
    private Long entrepriseId;

    public ContactJson() {
    }

    public ContactJson(Long id, String address, String nom, String prenom, String type, String numTVA) {
        this.id = id;
        this.address = address;
        this.nom = nom;
        this.prenom = prenom;
        this.type = type;
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

    public void setType(String type) {
        this.type = type;
    }

    public String getNumTVA() {
        return numTVA;
    }

    public void setNumTVA(String numTVA) {
        this.numTVA = numTVA;
    }

    public Long getEntrepriseId() {
        return entrepriseId;
    }

    public void setEntrepriseId(Long entrepriseId) {
        this.entrepriseId = entrepriseId;
    }
}
