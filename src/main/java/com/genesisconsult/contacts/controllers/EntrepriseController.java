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
 * Entreprise Controller  which contains the following methods:
 */
@RestController
public class EntrepriseController implements Constants {
    private static final Logger log = LoggerFactory.getLogger(EntrepriseController.class);
    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Autowired
    private ContactRepository contactRepository;

    /**
     * method that returns List of entreprise
     **/
    @GetMapping("/entreprises")
    List<EntrepriseJson> all() {
        List<EntrepriseJson> jsons = null;
        List<Entreprise> entreprises = null;
        entreprises = entrepriseRepository.findAll();
        if (!entreprises.isEmpty()) {
            jsons = Converter.convertEnterepriseToJson(entreprises);
        }
        return jsons;
    }

    /**
     * method that adds an entreprise
     **/
    @PostMapping("/entreprises")
    EntrepriseJson newEntreprise(@RequestBody EntrepriseJson json) {
        /**Check that the address is not null, and that it contains a certain number of characters**/
        if (MethodUtil.emptyString(json.getAddress()) || json.getAddress().length() < 4) {
            throw new EmptyFieldException(ADRESSE, 4);
        }
        /**Check that the numTVA is not null, and that it contains a certain number of digits**/
        if (MethodUtil.emptyString(json.getNumTVA()) || !json.getNumTVA().matches("\\d{9}")) {
            throw new EmptyFieldException(NUM_TVA, 9, 1);
        }
        Entreprise entreprise = null;
        entreprise = Converter.convertJsonToEntreprise(json);
        entrepriseRepository.save(entreprise);
        return json;
    }

    /**
     * method that returns an entreprise depending on id
     **/
    @GetMapping("/entreprises/{id}")
    EntrepriseJson one(@PathVariable Long id) {
        EntrepriseJson json = null;
        Entreprise entreprise = null;
        entreprise = entrepriseRepository.findById(id)
                .orElseThrow(() -> new EntrepriseNotFoundException(id));
        json = Converter.convertEntrepriseToJson(entreprise);
        return json;
    }

    /**
     * method that modifies an entreprise by id, otherwise it adds it
     **/
    @PutMapping("/entreprises/{id}")
    EntrepriseJson replaceEntreprise(@RequestBody EntrepriseJson json, @PathVariable Long id) {
        Entreprise entreprise = null;
        /**Check that the address is not null, and that it contains a certain number of characters**/
        if (MethodUtil.emptyString(json.getAddress()) || json.getAddress().length() < 4) {
            throw new EmptyFieldException(ADRESSE, 4);
        }
        /**Check that the numTVA is not null, and that it contains a certain number of digits**/
        if (MethodUtil.emptyString(json.getNumTVA()) || !json.getNumTVA().matches("\\d{9}")) {
            throw new EmptyFieldException(NUM_TVA, 9, 1);
        }
        entreprise = entrepriseRepository.findById(id).orElseGet(() -> null);
        /**fill in the modified fields*/
        if (entreprise != null) {
            entreprise.setAddress(json.getAddress());
            entreprise.setNumTVA(json.getNumTVA());
            entrepriseRepository.save(entreprise);
            return Converter.convertEntrepriseToJson(entreprise);
        } else {
            entreprise = Converter.convertJsonToEntreprise(json);
            entreprise.setId(id);
            entrepriseRepository.save(entreprise);
            return json;
        }
    }

    /**
     * method that removes an entreprise depending on id
     **/
    @DeleteMapping("/entreprises/{id}")
    ResponseEntity deleteEntreprise(@PathVariable Long id) {
        Entreprise entreprise = entrepriseRepository.findById(id)
                .orElseThrow(() -> new EntrepriseNotFoundException(id));
        if (entreprise != null) {
            entrepriseRepository.deleteJoinEntreprise(id);
            entreprise.removeEntrepriseFromContacts();
            entrepriseRepository.deleteById(id);
        } else
            new EntrepriseNotFoundException(id);

        return new ResponseEntity<>(ENTREPRISE_DELETED, HttpStatus.OK);
    }

    /**
     * method that adds a contact to entreprise
     * <p>
     * we are receiving just the ids of the two objects
     **/

    @PostMapping("/entreprises/add-contact")
    ContactEntrepriseJson newContactEntreprise(@RequestBody ContactEntrepriseJson json) {
        Entreprise entreprise = null;
        Freelance freelance = null;
        Employee employee = null;
        Contact contact = null;
        List<Entreprise> entreprises = null;
        /**Verify if the ids are correct*/
        entreprise = entrepriseRepository.findById(json.getIdEntreprise()).orElseThrow(() -> new EntrepriseNotFoundException(json.getIdEntreprise()));
        contact = contactRepository.findById(json.getIdContact()).orElseThrow(() -> new ContactNotFoundException(json.getIdContact()));
        /**getting list of entreprises, it use for bidirectional persistence*/
        entreprises = contactRepository.listEntreprises(json.getIdContact());
        if (entreprises.isEmpty()) {
            entreprises = new ArrayList<Entreprise>();
        }
        entreprises.add(entreprise);
        /**We will add a contact  to entreprise
         * according to contact type*/
        if (_F.equals(contact.getType())) {
            freelance = (Freelance) contact;
            freelance.setEntreprises(entreprises);
            entreprise.addContact(freelance);
            entrepriseRepository.save(entreprise);
            return json;

        }
        if (_E.equals(contact.getType())) {
            employee = (Employee) contact;
            employee.setEntreprises(entreprises);
            entreprise.addContact(employee);
            entrepriseRepository.save(entreprise);
            return json;
        } else
        /**if the type is incorrect*/
            throw new ContactTypeException();

    }

    /**
     * method that returns an List of contacts associated to entreprise
     **/
    @GetMapping("/entreprises/contacts/{id}")
    public List<Contact> getContacts(@PathVariable Long id) {
        List<Contact> contacts = null;
        List<ContactJson> jsons = null;
        contacts = (List<Contact>) entrepriseRepository.listContacts(id);
        if (!contacts.isEmpty()) {
            jsons = Converter.convertContactToJson(contacts);
        }
        return contacts;
    }
}
