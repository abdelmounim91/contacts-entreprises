package com.genesisconsult.contacts.repositories;

import com.genesisconsult.contacts.entities.Contact;
import com.genesisconsult.contacts.entities.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    /**
     * Update contact with TVA NUM
     */
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update contact set address = :address, nom = :nom, prenom = :prenom, contact_type = :type, numTVA = :numTVA where id = :id")
    public void updateToFreelance(@Param("id") Long id, @Param("address") String address, @Param("nom") String nom, @Param("prenom") String prenom,
                                  @Param("type") String type, @Param("numTVA") String numTVA);

    /**
     * Update contact without TVA NUM
     */
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update contact set address = :address, nom = :nom, prenom = :prenom, contact_type = :type, numTVA = NULL where id = :id")
    public void updateToEmployee(@Param("id") Long id, @Param("address") String address, @Param("nom") String nom, @Param("prenom") String prenom,
                                 @Param("type") String type);

    /**
     * List of entreprises of a contact
     */
    @Query(name = "listEntreprises")
    public List<Entreprise> listEntreprises(@Param("id") Long id);
}
