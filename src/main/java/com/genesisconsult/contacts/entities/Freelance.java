package com.genesisconsult.contacts.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@DiscriminatorValue("F")
public class Freelance extends Contact {
    /**
     * Must have a unique numTVA and exactly 9 digits
     */
    @Column(length = 9, unique=true)
    @NotNull
    @Size(min = 9, max = 9)
    private String numTVA;

    public Freelance() {
        super();
    }

    public Freelance(Long id, String address, String nom, String prenom, String numTVA) {
        super(id, address, nom, prenom);
        this.numTVA = numTVA;
    }

    public String getNumTVA() {
        return numTVA;
    }

    public void setNumTVA(String numTVA) {
        this.numTVA = numTVA;
    }
}
