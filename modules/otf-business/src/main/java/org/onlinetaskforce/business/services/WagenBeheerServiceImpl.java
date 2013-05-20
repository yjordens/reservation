package org.onlinetaskforce.business.services;

import org.onlinetaskforce.common.domain.Reservatie;
import org.onlinetaskforce.common.domain.Wagen;
import org.onlinetaskforce.common.dto.ZoekReservatieDto;
import org.onlinetaskforce.common.dto.ZoekWagenDto;
import org.onlinetaskforce.common.exceptions.BusinessException;
import org.onlinetaskforce.common.exceptions.BusinessExceptionKeys;
import org.onlinetaskforce.common.exceptions.MultipleBusinessException;
import org.onlinetaskforce.common.log.Log;
import org.onlinetaskforce.persistence.dao.WagenBeheerDao;
import org.onlinetaskforce.persistence.dao.WagenOntvangstDao;
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
public class WagenBeheerServiceImpl extends BaseOtfServiceImpl implements WagenBeheerService {
    private WagenBeheerDao wagenBeheerDao;
    private WagenOntvangstDao wagenOntvangstDao;
    private ReservatieBeheerService reservatieBeheerService;
    private MailService mailService;

    public Wagen addOrModify(Wagen wagen, MultipleBusinessException mbe) throws BusinessException {
        Wagen wagenTosave = wagen;
        if (wagenBeheerDao.exists(wagen.getId())) {
            //wijziging
            wagen.setWijzigingsTijdstip(new Date());
            wagen.setWijzigingsGebruikerId(ThreadContextInfo.getInstance().getCurrentGebruikerId());
            if (wagen.getActief() == Boolean.FALSE) {
                ZoekReservatieDto dto = new ZoekReservatieDto();
                dto.setWagenId(wagen.getId());
                dto.setOpen(new Date());
                List<Reservatie> reservaties = reservatieBeheerService.overview(dto);
                if ((mbe == null || mbe.hasUnacceptedBusinessExceptions()) && reservaties != null && reservaties.size() > 0) {
                    throw new BusinessException(BusinessExceptionKeys.BE_KEY_SAVE_WAGEN_INVOLVES_CANCELLATIONS);
                } else {
                    for (Reservatie reservatie1 : reservaties) {
                        reservatieBeheerService.annuleerReservatie(reservatie1);
                    }
                    if (reservaties != null && reservaties.size() > 0) {
                        mailService.sendOverviewAnnulatiesEmail(reservaties, ThreadContextInfo.getInstance().getCurrentGebruikerId());
                        return wagenBeheerDao.merge(wagenTosave);
                    }
                }
            }
        }
        try {
            return wagenBeheerDao.saveOrUpdate(wagenTosave);
        } catch (Exception e) {
            Log.error(this, e);
            throw new BusinessException(BusinessExceptionKeys.BE_KEY_SAVE_WAGEN_FAILURE);
        }
    }

    @Override
    public List<Wagen> overview(ZoekWagenDto dto) {
        return wagenBeheerDao.overview(dto);
    }

    @Autowired
    public void setWagenBeheerDao(WagenBeheerDao wagenBeheerDao) {
        this.wagenBeheerDao = wagenBeheerDao;
    }

    @Autowired
    public void setReservatieBeheerService(ReservatieBeheerService reservatieBeheerService) {
        this.reservatieBeheerService = reservatieBeheerService;
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
