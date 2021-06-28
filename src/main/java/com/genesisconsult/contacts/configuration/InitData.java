package com.genesisconsult.contacts.configuration;


import com.genesisconsult.contacts.ContactsApplication;
import com.genesisconsult.contacts.entities.Employee;
import com.genesisconsult.contacts.entities.Entreprise;
import com.genesisconsult.contacts.entities.Freelance;
import com.genesisconsult.contacts.repositories.ContactRepository;
import com.genesisconsult.contacts.repositories.EntrepriseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Fill database at the start of the application
 */
@Configuration
public class InitData {

    private static final Logger log = LoggerFactory.getLogger(InitData.class);

    @Bean
    CommandLineRunner initDatabase(EntrepriseRepository entrepriseRepository, ContactRepository contactRepository) {

        return args -> {
            log.info("Preloading Entreprise" + entrepriseRepository.save(new Entreprise(1L,"CASABLANCA","111222333")));
            log.info("Preloading Entreprise" + entrepriseRepository.save(new Entreprise(2L,"SAFI","111222444")));

            log.info("Preloading Employee" + contactRepository.save(new Employee(1L,"CASABLANCA", "RAHILE", "Abdelmounim")));
            log.info("Preloading Freelance" + contactRepository.save(new Freelance(2L,"CASABLANCA", "RAHILE", "Fatime ezzahra", "111222335")));
        };
    }
}
