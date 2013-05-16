package org.onlinetaskforce.persistence.dao;


import org.apache.commons.lang.StringUtils;
import org.onlinetaskforce.common.domain.Wagen;
import org.onlinetaskforce.common.dto.ZoekWagenDto;
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
@Repository("wagenBeheerDao")
@Transactional
public class WagenBeheerDaoImpl extends BaseDomainDaoImpl<Wagen, String> implements WagenBeheerDao {

    /**
     * Instantiates a new generic dao hibernate impl.
     */
     public WagenBeheerDaoImpl() {
        super(Wagen.class);
    }

    public List<Wagen> overview(ZoekWagenDto dto) {
        Map<String, Object> parameters = new HashMap<String, Object>();

        RelationalQuery hql = new RelationalQuery();
        hql.addSelectField("w");
        hql.setFrom("Wagen w");

        QueryCondition mainCondition = new QueryCondition();
        hql.setCondition(mainCondition);

        if (StringUtils.isNotBlank(dto.getNummerplaat())) {
            QueryCondition qc = new QueryCondition("w.nummerplaat = :np");
            mainCondition.logicalAnd(qc);
            parameters.put("np", dto.getNummerplaat());
        }
        if (StringUtils.isNotBlank(dto.getMerk())) {
            QueryCondition qc = new QueryCondition("w.merk = :m");
            mainCondition.logicalAnd(qc);
            parameters.put("m", dto.getMerk());
        }
        if (StringUtils.isNotBlank(dto.getMerktype())) {
            QueryCondition qc = new QueryCondition("w.merktype = :mt");
            mainCondition.logicalAnd(qc);
            parameters.put("mt", dto.getMerktype());
        }
        if (dto.getActief() != null) {
            QueryCondition qc = new QueryCondition("w.actief = :a");
            mainCondition.logicalAnd(qc);
            parameters.put("a", dto.getActief());
        }
        List<Wagen> result = getCurrentSession()
                .createQuery(hql.resolve())
                .setProperties(parameters)
                .setMaxResults(200)
                .list();
        return result;
    }
}
