package com.genesisconsult.contacts.errors;

/**
 * Exception ContactNotFoundException
 */
public class ContactNotFoundException extends RuntimeException {
    public ContactNotFoundException(Long id){
        super("Could not find contact " + id);
    }
}
