package com.genesisconsult.contacts.utils;

import com.genesisconsult.contacts.entities.Contact;
import com.genesisconsult.contacts.entities.Employee;
import com.genesisconsult.contacts.entities.Entreprise;
import com.genesisconsult.contacts.entities.Freelance;
import com.genesisconsult.contacts.jsons.ContactJson;
import com.genesisconsult.contacts.jsons.EntrepriseJson;

import java.util.ArrayList;
import java.util.List;

public class Converter implements Constants {
    /**
     * Convert Json to Freelance Entity
     */
    public static Freelance convertJsonToFreelance(ContactJson json) {
        Freelance contact = new Freelance();
        contact.setId(json.getId());
        contact.setAddress(json.getAddress());
        contact.setNom(json.getNom());
        contact.setPrenom(json.getPrenom());
        contact.setNumTVA(json.getNumTVA());
        return contact;
    }

    /**
     * Convert Json to Employee Entity
     */
    public static Employee convertJsonToEmployee(ContactJson json) {
        Employee contact = new Employee();
        contact.setId(json.getId());
        contact.setAddress(json.getAddress());
        contact.setNom(json.getNom());
        contact.setPrenom(json.getPrenom());
        return contact;
    }

    /**
     * Convert Freelance Entity to Json
     */
    public static ContactJson convertFreeLanceToJson(Freelance contact) {
        ContactJson json = new ContactJson();
        json.setId(contact.getId());
        json.setNom(contact.getNom());
        json.setPrenom(contact.getPrenom());
        json.setAddress(contact.getAddress());
        json.setType(_F);
        json.setNumTVA(contact.getNumTVA());
        return json;
    }

    /**
     * Convert Employee Entity to Json
     */
    public static ContactJson convertEmployeeToJson(Employee contact) {
        ContactJson json = new ContactJson();
        json.setId(contact.getId());
        json.setNom(contact.getNom());
        json.setPrenom(contact.getPrenom());
        json.setAddress(contact.getAddress());
        json.setType(_E);
        return json;
    }

    /**
     * Convert Entreprise Entity to Json
     */
    public static EntrepriseJson convertEntrepriseToJson(Entreprise entreprise) {
        EntrepriseJson json = new EntrepriseJson();
        json.setId(entreprise.getId());
        json.setAddress(entreprise.getAddress());
        json.setNumTVA(entreprise.getNumTVA());
        return json;
    }

    /**
     * Convert Json to Entreprise Entity
     */
    public static Entreprise convertJsonToEntreprise(EntrepriseJson json) {
        Entreprise entreprise = new Entreprise();
        entreprise.setId(json.getId());
        entreprise.setAddress(json.getAddress());
        entreprise.setNumTVA(json.getNumTVA());
        return entreprise;
    }

    /**
     * Convert List Contact Entity to List Json
     */
    public static List<ContactJson> convertContactToJson(List<Contact> contacts) {
        List<ContactJson> jsons = null;
        if (!contacts.isEmpty()) {
            jsons = new ArrayList<ContactJson>();
            for (Contact contact : contacts) {
                if (_F.equals(contact.getType()))
                    jsons.add(convertFreeLanceToJson((Freelance) contact));
                else
                    jsons.add(convertEmployeeToJson((Employee) contact));
            }
        }
        return jsons;
    }

    /**
     * Convert List Entreprise Entity to List Json
     */
    public static List<EntrepriseJson> convertEnterepriseToJson(List<Entreprise> entreprises) {
        List<EntrepriseJson> jsons = null;
        if (!entreprises.isEmpty()) {
            jsons = new ArrayList<EntrepriseJson>();
            for (Entreprise entreprise : entreprises) {
                jsons.add(convertEntrepriseToJson(entreprise));
            }
        }
        return jsons;
    }


}
