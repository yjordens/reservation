package org.onlinetaskforce.persistence.dao;

import org.onlinetaskforce.common.domain.Wagen;
import org.onlinetaskforce.common.dto.ZoekWagenDto;
import org.onlinetaskforce.common.exceptions.BusinessException;

import java.util.List;

/**
 * @author jordens
 * @since 10/03/13
 */
public interface WagenBeheerDao extends BaseDomainDao<Wagen, String> {
    /**
     * Gets all wagens form the carpool
     * @param dto supplier of the search criteria
     * @return List<Wagen>
     * @throws BusinessException
     */
    List<Wagen> overview(ZoekWagenDto dto);
}
