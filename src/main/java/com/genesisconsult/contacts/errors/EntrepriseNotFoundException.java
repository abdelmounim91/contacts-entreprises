package com.genesisconsult.contacts.errors;

/**
 * Exception EntrepriseNotFoundException
 */
public class EntrepriseNotFoundException extends RuntimeException {
    public EntrepriseNotFoundException(Long id){
        super("Could not find entreprise "+ id);
    }
}
