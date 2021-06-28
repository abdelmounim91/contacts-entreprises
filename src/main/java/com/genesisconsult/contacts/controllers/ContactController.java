package com.genesisconsult.contacts.controllers;


import com.genesisconsult.contacts.entities.Contact;
import com.genesisconsult.contacts.entities.Employee;
import com.genesisconsult.contacts.entities.Entreprise;
import com.genesisconsult.contacts.entities.Freelance;
import com.genesisconsult.contacts.errors.ContactNotFoundException;
import com.genesisconsult.contacts.errors.ContactTypeException;
import com.genesisconsult.contacts.errors.EmptyFieldException;
import com.genesisconsult.contacts.errors.EntrepriseNotFoundException;
import com.genesisconsult.contacts.jsons.ContactEntrepriseJson;
import com.genesisconsult.contacts.jsons.ContactJson;
import com.genesisconsult.contacts.jsons.EntrepriseJson;
import com.genesisconsult.contacts.repositories.ContactRepository;
import com.genesisconsult.contacts.repositories.EntrepriseRepository;
import com.genesisconsult.contacts.utils.Constants;
import com.genesisconsult.contacts.utils.Converter;
import com.genesisconsult.contacts.utils.MethodUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Contact Controller which contains the following methods:
 */
@RestController
public class ContactController implements Constants {
    private static final Logger log = LoggerFactory.getLogger(ContactController.class);
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    /**
     * method that returns List of contacts
     **/
    @GetMapping("/contacts")
    List<ContactJson> all() {
        List<Contact> contacts = null;
        List<ContactJson> jsons = null;
        contacts = contactRepository.findAll();
        jsons = Converter.convertContactToJson(contacts);
        return jsons;
    }

    /**
     * method that adds an contact
     **/
    @PostMapping("/contacts")
    ContactJson newContact(@RequestBody ContactJson json) {

        Freelance freelance = null;
        Employee employee = null;
        /**Check that type of contact is present and that it's an Employee or Freelance**/
        if (json.getType() == null || json.getType() == "" || (!_F.equals(json.getType()) && !_E.equals(json.getType()))) {
            throw new ContactTypeException();
        }
        /**Check that the nom, prenom, address is not null, and that it contains a certain number of characters**/
        if (MethodUtil.emptyString(json.getNom()) || json.getNom().length() < 2) {
            throw new EmptyFieldException(NOM, 2);
        }
        if (MethodUtil.emptyString(json.getPrenom()) || json.getPrenom().length() < 2) {
            throw new EmptyFieldException(PRENOM, 2);
        }
        if (MethodUtil.emptyString(json.getAddress()) || json.getAddress().length() < 4) {
            throw new EmptyFieldException(ADRESSE, 4);
        }
        /**According to the type we are adding a contact
         *
         * Check that the numTVA is not null, and that it contains a certain number of digits**/
        if (_F.equals(json.getType())) {
            if (MethodUtil.emptyString(json.getNumTVA()) || !json.getNumTVA().matches("\\d{9}")) {
                throw new EmptyFieldException(NUM_TVA, 9, 1);
            }
            freelance = new Freelance();
            freelance.setId(null);
            freelance.setNom(json.getNom());
            freelance.setPrenom(json.getPrenom());
            freelance.setAddress(json.getAddress());
            freelance.setNumTVA(json.getNumTVA());
            contactRepository.save(freelance);
            return json;
        }
        if (_E.equals(json.getType())) {
            employee = new Employee();
            employee.setId(null);
            employee.setNom(json.getNom());
            employee.setPrenom(json.getPrenom());
            employee.setAddress(json.getAddress());
            contactRepository.save(employee);
            return json;
        } else
            throw new ContactTypeException();

    }

    /**
     * method that returns an contact depending on id
     **/
    @GetMapping("/contacts/{id}")
    ContactJson one(@PathVariable Long id) {
        ContactJson json = null;
        Contact contact = null;
        contact = contactRepository.findById(id).orElseThrow(() -> new ContactNotFoundException(id));
        if (_F.equals(contact.getType())) {
            json = Converter.convertFreeLanceToJson((Freelance) contact);
        } else {
            json = Converter.convertEmployeeToJson((Employee) contact);
        }
        return json;
    }

    /**
     * method that modifies an contact by id, otherwise it adds it
     **/
    @PutMapping("/contacts/{id}")
    ContactJson replaceContact(@RequestBody ContactJson json, @PathVariable Long id) {
        Freelance freelance = null;
        Employee employee = null;
        Contact oldContact = null;
        /**Check that type of contact is present and that it's an Employee or Freelance**/
        if (json.getType() == null || json.getType() == "" || (!_F.equals(json.getType()) && !_E.equals(json.getType()))) {
            throw new ContactTypeException();
        }
        /**Check that the nom, prenom, address is not null, and that it contains a certain number of characters**/
        if (MethodUtil.emptyString(json.getNom()) || json.getNom().length() < 2) {
            throw new EmptyFieldException(NOM, 2);
        }
        if (MethodUtil.emptyString(json.getPrenom()) || json.getPrenom().length() < 2) {
            throw new EmptyFieldException(PRENOM, 2);
        }
        if (MethodUtil.emptyString(json.getAddress()) || json.getAddress().length() < 4) {
            throw new EmptyFieldException(ADRESSE, 4);
        }
        /**Check that the numTVA is not null, and that it contains a certain number of digits**/
        if (_F.equals(json.getType())) {
            if (MethodUtil.emptyString(json.getNumTVA()) || !json.getNumTVA().matches("\\d{9}")) {
                throw new EmptyFieldException(NUM_TVA, 9, 1);
            }
        }
        oldContact = contactRepository.findById(id).orElseGet(() -> null);
        /** if the contact is null we add it*/
        if (oldContact == null) {

            if (_F.equals(json.getType())) {

                freelance = Converter.convertJsonToFreelance(json);
                freelance.setId(id);
                contactRepository.save(freelance);
                return json;
            } else {

                employee = Converter.convertJsonToEmployee(json);
                employee.setId(id);
                contactRepository.save(employee);
                return json;
            }
        } else {
            /** if the contact is not null we modify it*/
            if (_F.equals(oldContact.getType()) && _F.equals(json.getType())) {

                freelance = Converter.convertJsonToFreelance(json);
                freelance.setId(id);
                contactRepository.save(freelance);
                return json;
            }
            if (_E.equals(oldContact.getType()) && _E.equals(json.getType())) {

                employee = Converter.convertJsonToEmployee(json);
                employee.setId(id);
                contactRepository.save(employee);
                return json;
            }

            if (_E.equals(oldContact.getType()) && _F.equals(json.getType())) {

                freelance = Converter.convertJsonToFreelance(json);
                freelance.setId(id);
                /**Using native query to force the update with TVA num*/
                contactRepository.updateToFreelance(id, json.getAddress(), json.getNom(), json.getPrenom(), json.getType(), json.getNumTVA());
                return json;
            }

            if (_F.equals(oldContact.getType()) && _E.equals(json.getType())) {

                employee = Converter.convertJsonToEmployee(json);
                employee.setId(id);
                /**Using native query to force the update without TVA num*/
                contactRepository.updateToEmployee(id, json.getAddress(), json.getNom(), json.getPrenom(), json.getType());
                return json;
            }
        }
        return null;
    }

    /**
     * method that removes a contact depending on id
     **/
    @DeleteMapping("/contacts/{id}")
    ResponseEntity deleteContact(@PathVariable Long id) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new ContactNotFoundException(id));
        if (contact != null) {
            contact.removeContactFromEntreprises();
            contactRepository.deleteById(id);
        } else
            new ContactNotFoundException(id);


        return new ResponseEntity<>(CONTACT_DELETED, HttpStatus.OK);
    }

    /**
     * method that adds an entreprise to a contact
     * <p>
     * we are receiving just the ids of the two objects
     **/
    @PostMapping("/contacts/add-entreprise")
    ContactEntrepriseJson newEntrepriseContact(@RequestBody ContactEntrepriseJson json) {
        Contact contact = null;
        Entreprise entreprise = null;
        List<Contact> contacts = null;
        if (json != null) {
            /**Verify if the ids are correct*/
            contact = contactRepository.findById(json.getIdContact()).orElseThrow(() -> new ContactNotFoundException(json.getIdContact()));
            entreprise = entrepriseRepository.findById(json.getIdEntreprise()).orElseThrow(() -> new EntrepriseNotFoundException(json.getIdEntreprise()));
            /**getting list of contacts, it use for bidirectional persistence*/
            contacts = entrepriseRepository.listContacts(json.getIdEntreprise());
            if (contacts.isEmpty()) {
                contacts = new ArrayList<Contact>();
            }

            entreprise.setContacts(contacts);
            contact.addEntreprise(entreprise);
            contactRepository.save(contact);

        } else {
            throw new EmptyFieldException();
        }
        return json;
    }

    /**
     * method that returns an List of entreprises associated to contact
     **/
    @GetMapping("/contacts/entreprises/{id}")
    public List<EntrepriseJson> getEntreprise(@PathVariable Long id) {
        List<Entreprise> entreprises = null;
        List<EntrepriseJson> jsons = null;
        entreprises = (List<Entreprise>) contactRepository.listEntreprises(id);
        if (!entreprises.isEmpty()) {
            jsons = Converter.convertEnterepriseToJson(entreprises);
        }
        return jsons;
    }

}

