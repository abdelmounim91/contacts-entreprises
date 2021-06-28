package com.genesisconsult.contacts.errors;

/**
 * Exception EmptyFieldException
 */
public class EmptyFieldException extends RuntimeException {
    public EmptyFieldException(String str, int count) {
        super("Field " + str + " must be completed and must have at least " + count + " characters.");
    }

    public EmptyFieldException(String str, int count, int d) {
        super("Field " + str + " must be completed and must have at least " + count + " chiffres.");
    }

    public EmptyFieldException() {
        super("Fields must be completed");
    }
}