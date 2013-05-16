package org.onlinetaskforce.business.services;

import org.onlinetaskforce.common.domain.Reservatie;
import org.onlinetaskforce.common.domain.Wagen;
import org.onlinetaskforce.common.domain.WagenOntvangst;
import org.onlinetaskforce.common.dto.ZoekReservatieDto;
import org.onlinetaskforce.common.exceptions.BusinessException;

import java.util.List;

/**
 * @author jordens
 * @since 21/04/13
 */
public interface ReservatieBeheerService {
    /**
     * adds a reservatie
     * @param reservatie the reservatie to add
     * @return Wagen
     * @throws BusinessException
     */
    public Reservatie addOrModify(Reservatie reservatie) throws BusinessException;

    /**
     * gets all reservations for the given user
     * @param dto supplier of the search criteria
     * @return List<Reservatie>
     */
    public List<Reservatie> overview(ZoekReservatieDto dto);

    /**
     * zoekt een wagen volgens automatisch methode om te reserveren op basis van de gegeven criteria
     * @param reservatie criteria om te zoeken
     * @return List<Wagen>
     */
    public List<Wagen> zoekAutomatisch(Reservatie reservatie);

    /**
     * Creates a reservation
     * @param reservatie the reservation to create
     * @return @see description
     */
    Reservatie createReservatie(Reservatie reservatie) throws BusinessException;

    /**
     * Cancels a reservation
     * @param reservatie the reservation to cancel
     */
    void annuleerReservatie(Reservatie reservatie) throws BusinessException;

    /**
     * Neemt een wagen in ontvangst
     * @param wagenOntvangst
     */
    void ontvangWagen(WagenOntvangst wagenOntvangst);

    /**
     * Searches a WagenOntvangst given a reservation
     * @param reservatie the criteria to search with
     * @return @see description
     */
    WagenOntvangst findWagenOntvangst(Reservatie reservatie);
}
