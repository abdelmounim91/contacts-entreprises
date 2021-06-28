package com.genesisconsult.contacts.utils;

public class MethodUtil {

    /**Verify a String if empty*/
    public static boolean emptyString(String str){
        if(str == null) return true;
        return str.trim().isEmpty();
    }
}
