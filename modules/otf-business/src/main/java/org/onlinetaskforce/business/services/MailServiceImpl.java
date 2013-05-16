package org.onlinetaskforce.business.services;

import org.onlinetaskforce.common.domain.Gebruiker;
import org.onlinetaskforce.common.domain.Reservatie;
import org.onlinetaskforce.common.dto.ContactFormDto;
import org.onlinetaskforce.common.email.OtfMailProperties;
import org.onlinetaskforce.common.exceptions.BusinessException;
import org.onlinetaskforce.common.exceptions.BusinessExceptionKeys;
import org.onlinetaskforce.common.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author jordens
 * @since 28/03/13
 */
@Service
public class MailServiceImpl extends BaseOtfServiceImpl implements MailService {
    private JavaMailSender otfMailSender;
    private GebruikerService gebruikerService;
    private Session javaMailSessionFactoryBean;

    public void sendMail(ContactFormDto dto) throws BusinessException {
        try {
            MimeMessage eMailMsg = otfMailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(eMailMsg, false, "UTF-8");
            String to = "yvan.jordens@googlemail.com";
            message.setTo(to);

            message.setSubject("OTF-contactform");
            message.setText(getContent(dto), false);
            message.setSentDate(new Date());
            otfMailSender.send(eMailMsg);
        } catch (MailException e) {
            Log.error(this, e);
            throw new BusinessException(BusinessExceptionKeys.BE_KEY_MAIL_SEND_FAILURE);
        } catch (MessagingException e) {
            Log.error(this, e);
            throw new BusinessException(BusinessExceptionKeys.BE_KEY_MAIL_SEND_FAILURE);
        }
    }

    @Override
    public void sendMail(Reservatie reservatie) throws BusinessException {
        try {
            Gebruiker gebruiker = gebruikerService.getGebruikerById(reservatie.getCreatieGebruikerId());

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            StringBuilder builder = new StringBuilder();
            if (reservatie.isAnnulatie()) {
                Gebruiker annuleerder = gebruikerService.getGebruikerById(reservatie.getAnnulatieGebruikerId());

                builder.append(OtfMailProperties.getInstance().getProperty("reservatie.mail.greet1")).append(" ").append(gebruiker.getFullName()).append("\n\n")
                .append(OtfMailProperties.getInstance().getProperty("reservatie.mail.text1")).append("\n")
                .append("Reservatienummer = ").append(reservatie.getReservatieNummer()).append("\n")
                .append("Periode start = ").append(sdf.format(reservatie.getBeginDatum())).append("\n")
                .append("Periode einde = ").append(sdf.format(reservatie.getEindDatum())).append("\n")
                .append("Wagen = ").append(reservatie.getWagen().getMerk()).append(" ").append(reservatie.getWagen().getMerktype()).append("\n")
                .append("Brandstof = ").append(reservatie.getWagen().getBrandstof()).append("\n")
                .append("Nummerplaat = ").append(reservatie.getWagen().getNummerplaat()).append("\n\n")
                .append("Annuleerder:").append(annuleerder.getFullName());
            } else {
                builder.append(OtfMailProperties.getInstance().getProperty("reservatie.mail.greet2")).append(" ").append(gebruiker.getFullName()).append("\n\n")
                .append(OtfMailProperties.getInstance().getProperty("reservatie.mail.text2")).append("\n")
                .append("Reservatienummer = ").append(reservatie.getReservatieNummer()).append("\n")
                .append("Periode start = ").append(sdf.format(reservatie.getBeginDatum())).append("\n")
                .append("Periode einde = ").append(sdf.format(reservatie.getEindDatum())).append("\n")
                .append("Wagen = ").append(reservatie.getWagen().getMerk()).append(" ").append(reservatie.getWagen().getMerktype()).append("\n")
                .append("Brandstof = ").append(reservatie.getWagen().getBrandstof()).append("\n")
                .append("Nummerplaat = ").append(reservatie.getWagen().getNummerplaat()).append("\n\n")
                .append("OPMERKING:").append("\n")
                .append(OtfMailProperties.getInstance().getProperty("reservatie.mail.rules"));
            }

            MimeMessage eMailMsg = otfMailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(eMailMsg, false, "UTF-8");
            message.setTo(gebruiker.getEmail());
            message.setSubject(OtfMailProperties.getInstance().getProperty("reservatie.mail.subject") + " " + reservatie.getReservatieNummer());
            message.setText(builder.toString(), false);
            message.setSentDate(new Date());
            otfMailSender.send(eMailMsg);
        } catch (MailException e) {
            Log.error(this, e);
            throw new BusinessException(BusinessExceptionKeys.BE_KEY_MAIL_SEND_FAILURE);
        } catch (MessagingException e) {
            Log.error(this, e);
            throw new BusinessException(BusinessExceptionKeys.BE_KEY_MAIL_SEND_FAILURE);
        }
    }

    @Override
    public void sendOverviewAnnulatiesEmail(List<Reservatie> reservaties, String currentGebruikerId) throws BusinessException {
        try {
            Gebruiker gebruiker = gebruikerService.getGebruikerById(currentGebruikerId);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            StringBuilder builder = new StringBuilder();
            builder.append(OtfMailProperties.getInstance().getProperty("overview.annualties.mail.greet")).append(" ").append(gebruiker.getFullName()).append("\n\n")
            .append(OtfMailProperties.getInstance().getProperty("overview.annualties.mail.text1")).append("\n")
            .append(OtfMailProperties.getInstance().getProperty("overview.annualties.mail.text2")).append(" ").append(reservaties.get(0).getWagen().getMerk()).append(" ").append(reservaties.get(0).getWagen().getMerktype()).append("\n")
            .append(OtfMailProperties.getInstance().getProperty("overview.annualties.mail.text3")).append(" ").append(reservaties.get(0).getWagen().getNummerplaat()).append("\n\n");

            builder.append(OtfMailProperties.getInstance().getProperty("overview.annualties.mail.text4")).append("\n");
            for (Reservatie reservatie : reservaties) {
                Gebruiker reserveerder = gebruikerService.getGebruikerById(reservatie.getCreatieGebruikerId());
                builder.append("Reservatienummer = ").append(reservatie.getReservatieNummer()).append(" | ")
                .append("Periode start = ").append(sdf.format(reservatie.getBeginDatum())).append(" ")
                .append("Periode einde = ").append(sdf.format(reservatie.getEindDatum())).append(" | ")
                .append("Reserveerder:").append(reserveerder.getFullName()).append("\n");
            }

            MimeMessage eMailMsg = otfMailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(eMailMsg, false, "UTF-8");
            message.setTo(gebruiker.getEmail());
            message.setSubject(OtfMailProperties.getInstance().getProperty("overview.annualties.mail.subject"));
            message.setText(builder.toString(), false);
            message.setSentDate(new Date());
            otfMailSender.send(eMailMsg);
        } catch (MailException e) {
            Log.error(this, e);
            throw new BusinessException(BusinessExceptionKeys.BE_KEY_MAIL_SEND_FAILURE);
        } catch (MessagingException e) {
            Log.error(this, e);
            throw new BusinessException(BusinessExceptionKeys.BE_KEY_MAIL_SEND_FAILURE);
        }
    }

    @Override
    public void sendWelcomeEmail(Gebruiker gebruiker) throws BusinessException {
        try {
            StringBuilder builder = new StringBuilder();

            builder.append(OtfMailProperties.getInstance().getProperty("welcome.mail.greet")).append(" ").append(gebruiker.getFullName()).append("\n\n")
            .append(OtfMailProperties.getInstance().getProperty("welcome.mail.text1")).append("\n")
            .append(OtfMailProperties.getInstance().getProperty("welcome.mail.text2")).append(" ").append(gebruiker.getUsername()).append("\n")
            .append(OtfMailProperties.getInstance().getProperty("welcome.mail.text3")).append(" ").append(Gebruiker.DEFAULT_WW_TXT).append("\n\n");

            builder.append(OtfMailProperties.getInstance().getProperty("welcome.mail.text4")).append(":\n");
            builder.append(OtfMailProperties.getInstance().getProperty("welcome.mail.text5")).append("\n\n");

            builder.append(OtfMailProperties.getInstance().getProperty("welcome.mail.text6")).append("\n");

            MimeMessage eMailMsg = otfMailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(eMailMsg, false, "UTF-8");
            message.setTo(gebruiker.getEmail());
            message.setSubject(OtfMailProperties.getInstance().getProperty("welcome.mail.subject"));
            message.setText(builder.toString(), false);
            message.setSentDate(new Date());
            otfMailSender.send(eMailMsg);
        } catch (MailException e) {
            Log.error(this, e);
            throw new BusinessException(BusinessExceptionKeys.BE_KEY_MAIL_SEND_FAILURE);
        } catch (MessagingException e) {
            Log.error(this, e);
            throw new BusinessException(BusinessExceptionKeys.BE_KEY_MAIL_SEND_FAILURE);
        }
    }

    private String getContent(ContactFormDto dto) {
        StringBuilder builder = new StringBuilder();
        builder.append("Naam: ").append(dto.getNaam()).append("\n")
                .append("Voornaam: ").append(dto.getVoornaam()).append("\n")
                .append("Straat: ").append(dto.getStraat()).append("\n")
                .append("Huisnummer: ").append(dto.getHuisnummer()).append("\n")
                .append("Postbus: ").append(dto.getPostbus()).append("\n")
                .append("Gemeente: ").append(dto.getGemeente()).append("\n")
                .append("Postcode: ").append(dto.getPostcode()).append("\n")
                .append("Telefoonnummer: ").append(dto.getTelefoon()).append("\n")
                .append("GSM: ").append(dto.getGsm()).append("\n")
                .append("Fax: ").append(dto.getFax()).append("\n")
                .append("Email: ").append(dto.getEmail()).append("\n");
        return builder.toString();
    }

    @Autowired
    public void setOtfMailSender(JavaMailSender otfMailSender) {
        this.otfMailSender = otfMailSender;
    }

    @Autowired
    public void setJavaMailSessionFactoryBean(Session javaMailSessionFactoryBean) {
        this.javaMailSessionFactoryBean = javaMailSessionFactoryBean;
    }

    @Autowired
    public void setGebruikerService(GebruikerService gebruikerService) {
        this.gebruikerService = gebruikerService;
    }
}
