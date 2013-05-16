package org.onlinetaskforce.persistence.dao;


import org.onlinetaskforce.common.domain.WagenOntvangst;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Concrete DAO implementation of BaseDomainDao's <T> objects.
 *
 * @author jordens
 * @since 15/03/13
 */
@Repository("wagenOntvangstDao")
@Transactional
public class WagenOntvangstDaoImpl extends BaseDomainDaoImpl<WagenOntvangst, String> implements WagenOntvangstDao {

    /**
     * Instantiates a new generic dao hibernate impl.
     */
     public WagenOntvangstDaoImpl() {
        super(WagenOntvangst.class);
    }

    @Override
    public WagenOntvangst findByReservatieId(String id) {
        String hql = "select wo from WagenOntvangst wo where wo.reservatie.id = :id";
        WagenOntvangst result = (WagenOntvangst) getCurrentSession()
                .createQuery(hql)
                .setString("id", id)
                .uniqueResult();
        return result;
    }
}
