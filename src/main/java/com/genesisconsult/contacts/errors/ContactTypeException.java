package com.genesisconsult.contacts.errors;

/**
 * Exception ContactTypeException
 */
public class ContactTypeException extends RuntimeException {

    public ContactTypeException(){
        super("Contact must have a type like 'F' for Freelance or 'E' for Employee");
    }
}
