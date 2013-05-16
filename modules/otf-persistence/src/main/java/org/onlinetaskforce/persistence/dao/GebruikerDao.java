package org.onlinetaskforce.persistence.dao;

import org.onlinetaskforce.common.domain.Gebruiker;
import org.onlinetaskforce.common.dto.ZoekGebruikerDto;

import java.util.List;

/**
 * @author jordens
 * @since 10/03/13
 */
public interface GebruikerDao extends BaseDomainDao<Gebruiker, String> {

    /**
     * Finds the Gebruiker for a given username
     *
     * @param username The username of the person
     * @return The found enitity, <code>null</code> if no result is found.
     */
    Gebruiker getGebruiker(String username);

    /**
     * Searches Gebruikers given the searcriteria
     * @param dto search criteria
     * @return List<Gebruiker>
     */
    List<Gebruiker> overview(ZoekGebruikerDto dto);
}
