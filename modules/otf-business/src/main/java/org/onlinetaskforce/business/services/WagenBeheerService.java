package org.onlinetaskforce.business.services;

import org.onlinetaskforce.common.domain.Wagen;
import org.onlinetaskforce.common.dto.ZoekWagenDto;
import org.onlinetaskforce.common.exceptions.BusinessException;
import org.onlinetaskforce.common.exceptions.MultipleBusinessException;

import java.util.List;

/**
 * @author jordens
 * @since 21/04/13
 */
public interface WagenBeheerService {
    /**
     * adds a wagen
     *
     * @param wagen the wagen to add
     * @param mbe MultipleBusinessException
     * @return Wagen the car to save
     * @throws BusinessException
     */
    public Wagen addOrModify(Wagen wagen, MultipleBusinessException mbe) throws BusinessException;

    /**
     * gets all wagens for the given user
     * @param dto supplier of the search criteria
     * @return List<Wagen>
     */
    public List<Wagen> overview(ZoekWagenDto dto);
}
