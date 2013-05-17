package org.onlinetaskforce.persistence.dao;


import org.apache.commons.lang.StringUtils;
import org.onlinetaskforce.common.domain.Reservatie;
import org.onlinetaskforce.common.domain.Wagen;
import org.onlinetaskforce.common.dto.ZoekReservatieDto;
import org.onlinetaskforce.persistence.helpers.QueryCondition;
import org.onlinetaskforce.persistence.helpers.RelationalQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Concrete DAO implementation of BaseDomainDao's <T> objects.
 *
 * @author jordens
 * @since 15/03/13
 */
@Repository("reservatieBeheerDao")
@Transactional
public class ReservatieBeheerDaoImpl extends BaseDomainDaoImpl<Reservatie, String> implements ReservatieBeheerDao {

    /**
     * Instantiates a new generic dao hibernate impl.
     */
     public ReservatieBeheerDaoImpl() {
        super(Reservatie.class);
    }

    public List<Reservatie> overview(ZoekReservatieDto dto) {
        Map<String, Object> parameters = new HashMap<String, Object>();

        RelationalQuery hql = new RelationalQuery();
        hql.addSelectField("r");
        hql.setFrom("Reservatie r");

        QueryCondition mainCondition = new QueryCondition();
        hql.setCondition(mainCondition);

        if (StringUtils.isNotBlank(dto.getGebruikersnaam())) {
            QueryCondition qc = new QueryCondition("r.creatieGebruikerId = (select g.id from Gebruiker g where g.username = :un)");
            mainCondition.logicalAnd(qc);
            parameters.put("un", dto.getGebruikersnaam());
        }
        if (StringUtils.isNotBlank(dto.getNummerplaat())) {
            QueryCondition qc = new QueryCondition("r.wagen.nummerplaat = :np");
            mainCondition.logicalAnd(qc);
            parameters.put("np", dto.getNummerplaat());
        }
        if (dto.getReservatieNummer() != null) {
            QueryCondition qc = new QueryCondition("r.reservatieNummer= :nr");
            mainCondition.logicalAnd(qc);
            parameters.put("nr", dto.getReservatieNummer());
        }
        if (dto.getOvertijd() != null && dto.getOvertijd()) {
            QueryCondition qc = new QueryCondition("r.eindDatum <= CURRENT_TIMESTAMP and not exists (select wo.id from WagenOntvangst wo where wo.reservatie.id = r.id) and r.annulatieTijdstip is null");
            mainCondition.logicalAnd(qc);
        }
        if (StringUtils.isNotBlank(dto.getWagenId())) {
            QueryCondition qc = new QueryCondition("r.wagen.id = :wid");
            mainCondition.logicalAnd(qc);
            parameters.put("wid", dto.getWagenId());
        }
        if (dto.getOpen() != null) {
            QueryCondition qc = new QueryCondition("r.eindDatum >= :open and not exists (select wo.id from WagenOntvangst wo where wo.reservatie.id = r.id) and r.annulatieTijdstip is null");
            mainCondition.logicalAnd(qc);
            parameters.put("open", dto.getOpen());
        }
        hql.addOrderByField("r.reservatieNummer desc");

        List<Reservatie> result = getCurrentSession()
                .createQuery(hql.resolve())
                .setProperties(parameters)
                .setMaxResults(200)
                .list();
        return result;
    }

    @Override
    public List<Wagen> zoekAutomatisch(Reservatie reservatie) {
        Map<String, Object> parameters = new HashMap<String, Object>();

        RelationalQuery hql = new RelationalQuery();
        hql.addSelectField("w");
        hql.setFrom("Wagen w");

        QueryCondition mainCondition = new QueryCondition("w.actief = true");
        hql.setCondition(mainCondition);

        QueryCondition qc = new QueryCondition("not exists (select r.id from Reservatie r where r.eindDatum >= :begdte and r.eindDatum <= :einddte and" +
                " r.wagen.id = w.id and r.annulatieGebruikerId is null and" +
                " not exists (select wo.id from WagenOntvangst wo where wo.reservatie.id = r.id))");
        mainCondition.logicalAnd(qc);
        parameters.put("begdte", reservatie.getBeginDatum());
        parameters.put("einddte", reservatie.getEindDatum());

        List<Wagen> result = getCurrentSession()
                .createQuery( hql.resolve())
                .setProperties(parameters)
                .list();
        return result;    }

    @Override
    public BigInteger getReservateNummer() {
        String sql = "select nextval('reservatie_seq')";
        return (BigInteger)getCurrentSession().createSQLQuery(sql).uniqueResult();  //To change body of implemented methods use File | Settings | File Templates.
    }
}
