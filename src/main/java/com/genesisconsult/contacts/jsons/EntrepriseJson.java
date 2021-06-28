package com.genesisconsult.contacts.jsons;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Json used for adding or modify an Entreprise
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class EntrepriseJson {

    private Long id;
    private String address;
    private String numTVA;
    private Long contactId;

    public EntrepriseJson() {
    }

    public EntrepriseJson(Long id, String address, String numTVA) {
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

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }
}
