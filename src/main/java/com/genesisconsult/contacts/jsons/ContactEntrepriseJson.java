package com.genesisconsult.contacts.jsons;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Json used for adding an entreprise to contact or vis-versa
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ContactEntrepriseJson {

    private Long idContact;
    private Long idEntreprise;

    public ContactEntrepriseJson() {
    }

    public ContactEntrepriseJson(Long idContact, Long idEntreprise) {
        this.idContact = idContact;
        this.idEntreprise = idEntreprise;
    }

    public Long getIdContact() {
        return idContact;
    }

    public void setIdContact(Long idContact) {
        this.idContact = idContact;
    }

    public Long getIdEntreprise() {
        return idEntreprise;
    }

    public void setIdEntreprise(Long idEntreprise) {
        this.idEntreprise = idEntreprise;
    }
}
