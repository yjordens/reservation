package org.onlinetaskforce.persistence.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * The base superclass for all DAO's of the otf project.
 * @author jordens
 * @since 15/03/13
 */
public class BaseOtfDaoImpl {
    /**
     * The QUERY paging currentpagingpagenumber default.
     */
    public static final int QUERY_PAGING_CURRENTPAGINGPAGENUMBER_DEFAULT = 1;

    /**
     * The JdbcTemplate that can be used for plain SQL queries in concrete DAO's.
     */
    private JdbcTemplate jdbcTemplate;

    /**
     * The NamedParameterJdbcTemplate that can be used for plain SQL queries in concrete DAO's.
     */
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * The session factory.
     */
    @SuppressWarnings("unused")
    private SessionFactory sessionFactory;

    /**
     * Hulp methode om een query uit te voeren via SQL om een bepaald aantal op te vragen uit de databank. Dit soort van queries heeft telkens maar één resultaat van het type int.
     *
     * @param sql De sql query om een bepaald aantal te tellen.
     * @param params De parameter die bij de query horen.
     * @return Het in de query opgevraagde aantal, 0 indien de query géén resultaat geeft.
     * @throws org.springframework.dao.IncorrectResultSizeDataAccessException indien meer dan één resultaat gevonden in de query.
     */
    protected int queryCountUsingSQL(String sql, Object[] params) {
        Integer result = (Integer) DataAccessUtils.uniqueResult(getJdbcTemplate().query(sql, params, new RowMapper() { // NOSONAR
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt(1);
            }
        }));
        if (result == null) {
            return 0;
        }
        return result;
    }

    /**
     * Hulp methode om een query uit te voeren via SQL om een bepaald aantal op te vragen uit de databank. Dit soort van queries heeft telkens maar één resultaat van het type int.
     *
     * @param sql De sql query om een bepaald aantal te tellen.
     * @param params De parameter die bij de query horen.
     * @return Het in de query opgevraagde aantal, 0 indien de query géén resultaat geeft.
     * @throws org.springframework.dao.IncorrectResultSizeDataAccessException indien meer dan één resultaat gevonden in de query.
     */
    protected int queryCountUsingSQLAndNamedParams(String sql, Map<String, Object> params) {
        return ((BigDecimal) getCurrentSession()
                .createSQLQuery(sql)
                .setProperties(params)
                .uniqueResult()).intValue();
    }



    /**
     * Shortcut method to retrieve the DataSource
     * @return See description
     */
    @SuppressWarnings("unused")
    protected DataSource getDataSource() {
        return getJdbcTemplate().getDataSource();
    }

    /**
     * Shortcut method to retrieve the current hibernate session.
     * @return See description
     */
    @SuppressWarnings("unused")
    protected Session getCurrentSession() {
        return getSessionFactory().getCurrentSession();
    }

    // Dependencies
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Autowired
    @Qualifier("otfSessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @SuppressWarnings("unused")
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @SuppressWarnings("unused")
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }
}
