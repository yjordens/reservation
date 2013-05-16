package org.onlinetaskforce.persistence.dao;

import org.onlinetaskforce.common.domain.Reservatie;
import org.onlinetaskforce.common.domain.Wagen;
import org.onlinetaskforce.common.dto.ZoekReservatieDto;

import java.math.BigInteger;
import java.util.List;

/**
 * @author jordens
 * @since 10/03/13
 */
public interface ReservatieBeheerDao extends BaseDomainDao<Reservatie, String> {
    /**
     * Gets all reservationsfor the given criteria
     * @param dto supplier of the search criteria
     * @return List<Reservatie>
     * @throws org.onlinetaskforce.common.exceptions.BusinessException
     */
    List<Reservatie> overview(ZoekReservatieDto dto);

    /**
     * zoekt een wagen volgens automatisch methode om te reserveren op basis van de gegeven criteria
     * @param reservatie criteria om te zoeken
     * @return List<Wagen>
     */
    List<Wagen> zoekAutomatisch(Reservatie reservatie);

    /**
     * Gets the next reservationNumber
     * @return Reservatie
     */
    BigInteger getReservateNummer();
}
