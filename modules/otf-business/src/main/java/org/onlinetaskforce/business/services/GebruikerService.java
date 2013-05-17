package org.onlinetaskforce.business.services;

import org.onlinetaskforce.common.domain.Gebruiker;
import org.onlinetaskforce.common.dto.ZoekGebruikerDto;
import org.onlinetaskforce.common.exceptions.BusinessException;

import java.util.List;

/**
 * @author jordens
 * @since 10/03/13
 */
public interface GebruikerService {
    /**
     * Loads a Gebruiker with the given name
     * @param naam the name of the Gebruiker to load
     * @return Gebruiker or null
     */
    public Gebruiker getGebruiker(String naam);

    /**
     * Loads a Gebruiker by its id
     * @param id identifier of the Gebruiker
     * @return Gebruiker or null
     */
    public Gebruiker getGebruikerById(String id);

    /**
     * Adds a picture to the Gebruiker
     * @param gebruiker the Gebruiker to save
     */
    public void savePicture(Gebruiker gebruiker);

    /**
     * adds a Gebruiker
     * @param gebruiker the gebruiker to add
     * @return Gebruiker
     * @throws BusinessException
     */
    public Gebruiker addOrModify(Gebruiker gebruiker) throws BusinessException;

    /**
     * gets all gebruikers for the given criteria
     * @param dto supplier of the search criteria
     * @return List<Gebruiker>
     */
    public List<Gebruiker> overview(ZoekGebruikerDto dto);

    /**
     * Changes the pssword for the given Gebruiker
     * @param id Identifier of the Gebruiker who's password needs to be changed
     * @param oldPw the original pw (encrypted)
     * @param newPw the new pw (encrypted)
     * @throws BusinessException
     */
    Gebruiker changePassword(String id, String oldPw, String newPw) throws BusinessException;

    /**
     * resets te wachtwoord to the default and sends an email to the gebruiker
     * @param gebruiker the user whos password needs to be reset
     * @return
     */
    Gebruiker resetWachtwoord(Gebruiker gebruiker) throws BusinessException;
}
