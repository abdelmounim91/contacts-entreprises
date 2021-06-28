package com.genesisconsult.contacts;

import com.genesisconsult.contacts.entities.Contact;
import com.genesisconsult.contacts.entities.Employee;
import com.genesisconsult.contacts.entities.Entreprise;
import com.genesisconsult.contacts.entities.Freelance;
import com.genesisconsult.contacts.repositories.ContactRepository;
import com.genesisconsult.contacts.repositories.EntrepriseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ContactsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContactsApplication.class, args);
    }


}
