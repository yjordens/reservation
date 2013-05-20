package org.onlinetaskforce.business.services;

import org.onlinetaskforce.common.domain.Reservatie;
import org.onlinetaskforce.common.domain.Wagen;
import org.onlinetaskforce.common.domain.WagenOntvangst;
import org.onlinetaskforce.common.dto.ZoekReservatieDto;
import org.onlinetaskforce.common.exceptions.BusinessException;
import org.onlinetaskforce.common.exceptions.BusinessExceptionKeys;
import org.onlinetaskforce.common.log.Log;
import org.onlinetaskforce.persistence.dao.ReservatieBeheerDao;
import org.onlinetaskforce.persistence.dao.WagenOntvangstDao;
import org.onlinetaskforce.persistence.utils.ThreadContextInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author jordens
 * @since 10/03/13
 */
@Service
@Transactional
public class reservatieBeheerServiceImpl extends BaseOtfServiceImpl implements ReservatieBeheerService {
    private ReservatieBeheerDao reservatieBeheerDao;
    private WagenOntvangstDao wagenOntvangstDao;
    private MailService mailService;

    public Reservatie addOrModify(Reservatie reservatie) throws BusinessException {
        try {
            return reservatieBeheerDao.saveOrUpdate(reservatie);
        } catch (Exception e) {
            Log.error(this, e);
            throw new BusinessException(BusinessExceptionKeys.BE_KEY_SAVE_RESERVATIE_FAILURE);
        }
    }

    @Override
    public List<Reservatie> overview(ZoekReservatieDto dto) {
        return reservatieBeheerDao.overview(dto);
    }

    @Override
    public List<Wagen> zoekAutomatisch(Reservatie reservatie) {
        return reservatieBeheerDao.zoekAutomatisch(reservatie);
    }

    @Override
    public Reservatie createReservatie(Reservatie reservatie) throws BusinessException {
        reservatie.setCreatieGebruikerId(ThreadContextInfo.getInstance().getCurrentGebruikerId());
        reservatie.setCreatieTijdstip(new Date());
        BigInteger reservatieNummer = reservatieBeheerDao.getReservateNummer();
        reservatie.setReservatieNummer(reservatieNummer.longValue());

        Reservatie newReservatie = reservatieBeheerDao.saveOrUpdate(reservatie);
        mailService.sendMail(reservatie);
        return newReservatie;
    }

    @Override
    public void annuleerReservatie(Reservatie reservatie) throws BusinessException {
        reservatie.setAnnulatieTijdstip(new Date());
        reservatie.setAnnulatieGebruikerId(ThreadContextInfo.getInstance().getCurrentGebruikerId());
        mailService.sendMail(reservatie);
        reservatieBeheerDao.saveOrUpdate(reservatie);
    }

    @Override
    public void ontvangWagen(WagenOntvangst wagenOntvangst, Reservatie reservatie) {
        wagenOntvangst.setCreatieGebruikerId(ThreadContextInfo.getInstance().getCurrentGebruikerId());
        wagenOntvangst.setCreatieTijdstip(new Date());
        wagenOntvangstDao.saveOrUpdate(wagenOntvangst);

        reservatie.setWagenOntvangst(wagenOntvangst);
        reservatieBeheerDao.merge(reservatie);
    }

    @Autowired
    public void setReservatieBeheerDao(ReservatieBeheerDao reservatieBeheerDao) {
        this.reservatieBeheerDao = reservatieBeheerDao;
    }

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @Autowired
    public void setWagenOntvangstDao(WagenOntvangstDao wagenOntvangstDao) {
        this.wagenOntvangstDao = wagenOntvangstDao;
    }
}
