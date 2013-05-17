package org.onlinetaskforce.business.services;

import org.onlinetaskforce.common.domain.Gebruiker;
import org.onlinetaskforce.common.domain.Reservatie;
import org.onlinetaskforce.common.dto.ContactFormDto;
import org.onlinetaskforce.common.exceptions.BusinessException;

import java.util.List;

/**
 * @author jordens
 * @since 28/03/13
 */
public interface MailService {
    /**
     * Sends an email to the OTF mailbox with the content provided
     * @param dto representation of what a user entered via the contact form
     */
    void sendMail(ContactFormDto dto) throws BusinessException;

    /**
     * Sends an email with the reservation details
     * @param reservatie
     * @throws BusinessException
     */
    void sendMail(Reservatie reservatie) throws BusinessException;

    /**
     * Sends an email with an overiview of cancellations due to modification on a car's attribute
     * @param reservaties List<Reservatie>
     * @param currentGebruikerId the user responsible for the cancellations
     */
    void sendOverviewAnnulatiesEmail(List<Reservatie> reservaties, String currentGebruikerId) throws BusinessException;

    /**
     * Sends a welcome email with instructions
     * @param gebruiker
     */
    void sendWelcomeEmail(Gebruiker gebruiker) throws BusinessException;

    /**
     * Sends a email to the given user to cionfirm his/her password reset with instructions
     * @param gebruiker the user to send te email to
     */
    void sendResetWachtwoordEmail(Gebruiker gebruiker) throws BusinessException;
}
