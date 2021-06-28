package com.genesisconsult.contacts.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("E")
public class Employee extends Contact{

    public Employee() {
        super();
    }

    public Employee(Long id, String address, String nom, String prenom) {
        super(id, address, nom, prenom);
    }
}
