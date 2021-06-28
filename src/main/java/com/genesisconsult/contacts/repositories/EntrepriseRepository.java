package com.genesisconsult.contacts.repositories;

import com.genesisconsult.contacts.entities.Contact;
import com.genesisconsult.contacts.entities.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {

    /**
     * List of contacts of a entreprise
     */
    @Query(name = "listContact")
    public List<Contact> listContacts(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM entreprise_contact where entreprise_id = :id")
    public void deleteJoinEntreprise(@Param("id") Long id);
}
