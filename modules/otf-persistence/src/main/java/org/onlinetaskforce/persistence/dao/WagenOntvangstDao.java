package org.onlinetaskforce.persistence.dao;

import org.onlinetaskforce.common.domain.WagenOntvangst;

/**
 * @author jordens
 * @since 10/03/13
 */
public interface WagenOntvangstDao extends BaseDomainDao<WagenOntvangst, String> {
    /**
     * Finds a WagenOntvangst given the reservation id
     * @param id Reservatie.id
     * @return WagenOntvangst or NULL
     */
    WagenOntvangst findByReservatieId(String id);
}
