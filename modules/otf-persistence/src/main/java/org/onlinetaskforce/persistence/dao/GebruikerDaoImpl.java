package org.onlinetaskforce.persistence.dao;

import org.apache.commons.lang.StringUtils;
import org.onlinetaskforce.common.domain.Gebruiker;
import org.onlinetaskforce.common.dto.ZoekGebruikerDto;
import org.onlinetaskforce.persistence.helpers.QueryCondition;
import org.onlinetaskforce.persistence.helpers.RelationalQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Concrete DAO implementation of BaseDomainDao's <T> objects.
 *
 * @author jordens
 * @since 15/03/13
 */
@Repository("gebruikerDao")
@Transactional
public class GebruikerDaoImpl extends BaseDomainDaoImpl<Gebruiker, String> implements GebruikerDao {

    /**
     * Instantiates a new generic dao hibernate impl.
     */
     public GebruikerDaoImpl() {
        super(Gebruiker.class);
    }

    @Override
    public Gebruiker getGebruiker(String username) {
        String hql = "select gbr from Gebruiker gbr where gbr.username = :username";
        Gebruiker result = (Gebruiker) getCurrentSession()
                .createQuery(hql)
                .setString("username", username)
                .uniqueResult();
        return result;
    }

    @Override
    public List<Gebruiker> overview(ZoekGebruikerDto dto) {
        Map<String, Object> parameters = new HashMap<String, Object>();

        RelationalQuery hql = new RelationalQuery();
        hql.addSelectField("g");
        hql.setFrom("Gebruiker g");

        QueryCondition mainCondition = new QueryCondition();
        hql.setCondition(mainCondition);

        if (StringUtils.isNotBlank(dto.getGebruikersnaam())) {
            QueryCondition qc = new QueryCondition("g.username= :un");
            mainCondition.logicalAnd(qc);
            parameters.put("un", dto.getGebruikersnaam());
        }
        if (StringUtils.isNotBlank(dto.getNaam())) {
            QueryCondition qc = new QueryCondition("g.naam = :n");
            mainCondition.logicalAnd(qc);
            parameters.put("n", dto.getNaam());
        }
        if (StringUtils.isNotBlank(dto.getVoornaam())) {
            QueryCondition qc = new QueryCondition("g.voornaam = :vn");
            mainCondition.logicalAnd(qc);
            parameters.put("vn", dto.getVoornaam());
        }
        if (dto.getActief() != null) {
            QueryCondition qc = new QueryCondition("g.actief = :a");
            mainCondition.logicalAnd(qc);
            parameters.put("a", dto.getActief());
        }
        List<Gebruiker> result = getCurrentSession()
                .createQuery(hql.resolve())
                .setProperties(parameters)
                .setMaxResults(200)
                .list();
        return result;    }
}
