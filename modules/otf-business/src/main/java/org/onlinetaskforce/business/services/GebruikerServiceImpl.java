package org.onlinetaskforce.business.services;

import org.onlinetaskforce.common.domain.Gebruiker;
import org.onlinetaskforce.common.dto.ZoekGebruikerDto;
import org.onlinetaskforce.common.exceptions.BusinessException;
import org.onlinetaskforce.common.exceptions.BusinessExceptionKeys;
import org.onlinetaskforce.common.log.Log;
import org.onlinetaskforce.persistence.dao.GebruikerDao;
import org.onlinetaskforce.persistence.utils.ThreadContextInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author jordens
 * @since 10/03/13
 */
@Service
@Transactional
public class GebruikerServiceImpl extends BaseOtfServiceImpl implements GebruikerService{
    private MailService mailService;
    private GebruikerDao gebruikerDao;

    public Gebruiker getGebruiker(String naam) {
        return gebruikerDao.getGebruiker(naam);
    }

    @Override
    public Gebruiker getGebruikerById(String id) {
        return gebruikerDao.get(id);
    }

    public void savePicture(Gebruiker gebruiker) {
        gebruikerDao.saveOrUpdate(gebruiker);
    }

    @Override
    public Gebruiker addOrModify(Gebruiker gebruiker) throws BusinessException {
        try {
            if (!gebruikerDao.exists(gebruiker.getId())) {
               //new gebruiker send welcome email
                gebruiker.setWachtwoord(Gebruiker.DEFAULT_WW);
                Gebruiker g = gebruikerDao.saveOrUpdate(gebruiker);
                mailService.sendWelcomeEmail(gebruiker);
                return g;
            } else {
                gebruiker.setWijzigingsTijdstip(new Date());
                gebruiker.setWijzigingsGebruikerId(ThreadContextInfo.getInstance().getCurrentGebruikerId());
                return gebruikerDao.merge(gebruiker);
            }
        } catch (Exception e) {
            Log.error(this, e);
            throw new BusinessException(BusinessExceptionKeys.BE_KEY_SAVE_USER_FAILURE);
        }
    }

    @Override
    public List<Gebruiker> overview(ZoekGebruikerDto dto) {
        return gebruikerDao.overview(dto);
    }

    @Override
    public Gebruiker changePassword(String id, String oldPw, String newPw) throws BusinessException {
        Gebruiker gebruiker = gebruikerDao.get(id);
        if (!gebruiker.getWachtwoord().equals(oldPw)) {
            throw new BusinessException(BusinessExceptionKeys.BE_KEY_WW_OLD_INVALID);
        }
        if (oldPw.equals(newPw)) {
            throw new BusinessException(BusinessExceptionKeys.BE_KEY_WW_OLD_EQUALS_NEW);
        }
        gebruiker.setWachtwoord(newPw);
        return gebruikerDao.saveOrUpdate(gebruiker);
    }

    @Override
    public Gebruiker resetWachtwoord(Gebruiker gebruiker) throws BusinessException {
        gebruiker.setWachtwoord(Gebruiker.DEFAULT_WW);
        gebruiker = gebruikerDao.merge(gebruiker);
        mailService.sendResetWachtwoordEmail(gebruiker);
        return gebruiker;
    }

    @Autowired
    public void setGebruikerDao(GebruikerDao gebruikerDao) {
        this.gebruikerDao = gebruikerDao;
    }

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }
}
