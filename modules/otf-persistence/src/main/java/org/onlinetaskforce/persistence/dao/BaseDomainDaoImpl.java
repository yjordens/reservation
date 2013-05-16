package org.onlinetaskforce.persistence.dao;

import org.hibernate.exception.ConstraintViolationException;
import org.onlinetaskforce.common.exceptions.BusinessException;
import org.onlinetaskforce.common.log.Log;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.ObjectRetrievalFailureException;

import java.io.Serializable;
import java.util.List;

/**
 * This class is created from the HP Techstack (be.vlaanderen.techstack.basis.dao.GenericDaoHibernateImpl)
 * The base superclass for all domain DAO's of the otf project.
 * @param <T>  the generic type
 * @param <PK> the generic type
 * @author jordens
 * @since 15/03/13
 */
public class BaseDomainDaoImpl<T, PK extends Serializable> extends BaseOtfDaoImpl implements BaseDomainDao<T, PK> {

    /**
     * The persistent class.
     */
    private Class<T> persistentClass;


    /**
     * Instantiates a new generic dao hibernate impl.
     *
     * @param persistentClass the persistent class
     */
    public BaseDomainDaoImpl(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return getCurrentSession().createQuery("select o from " + this.persistentClass.getName() + " o").list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(PK id) {
        return (T) getCurrentSession().load(this.persistentClass, id);
    }

    @Override
    public T load(PK id) throws ObjectRetrievalFailureException {
        T entity = (T) getCurrentSession().get(this.persistentClass, id);
        assertEntityRetrieved(entity, id);
        return entity;
    }

    @Override
    public boolean exists(PK id) {
        Object entity = getCurrentSession().get(persistentClass, id);
        getCurrentSession().evict(entity);
        return entity != null;
    }

    public T merge(T object) {
        getCurrentSession().merge(object);
        return object;
    }

    /**
     * Save.
     *
     * @param object the object
     * @return the t
     */
    public T save(T object) {
        getCurrentSession().save(object);
        return object;
    }

    /**
     * Save.
     *
     * @param entityName the entity name
     * @param object     the object
     * @return the t
     */
    public T save(String entityName, T object) {
        getCurrentSession().save(entityName, object);
        return object;
    }

    /**
     * Update.
     *
     * @param object the object
     * @return the t
     */
    public T update(T object) {
        getCurrentSession().update(object);
        return object;
    }

    /**
     * Update.
     *
     * @param entityName the entity name
     * @param object     the object
     * @return the t
     */
    public T update(String entityName, T object) {
        getCurrentSession().update(entityName, object);
        return object;
    }

    @Override
    public T saveOrUpdate(T object) {
        getCurrentSession().saveOrUpdate(object);
        return object;
    }

    @Override
    public T saveOrUpdateAndCheckConstraints(T object) throws BusinessException {
        try {
            saveOrUpdate(object);
            getCurrentSession().flush();
        } catch (ConstraintViolationException e) {
            // This implementation leans upon the Hibernate ViolatedConstraintNameExtracter of the PostgresqlDialect.
            // A dutch implementation is implemented on the otf PostgresqlDialect.
            String constraintName = e.getConstraintName();
            int positionLastDot = constraintName.lastIndexOf('.');
            if (positionLastDot != -1) {
                // If necessary, we remove the schema owner name (ex. DISDBPDE.UN01_GRP -> UN01_GRP)
                constraintName = constraintName.substring(positionLastDot + 1);
                constraintToBusinessExceptionDuringSaveOrUpdate(constraintName);
            }
            throw e; // When nog BusinessException is thrown in this logic, throw the original exception.
        }
        return object;
    }

    protected void constraintToBusinessExceptionDuringSaveOrUpdate(String constraintName) throws BusinessException{
        // This generic implementation does not map any constraint, override this method in specific DAO implementations when necessary.
    }

    @Override
    public void remove(T entity) {
        getCurrentSession().delete(entity);
    }

    /**
     * Assert entity retrieved.
     *
     * @param entity the entity
     * @param id     the id
     * @throws org.springframework.orm.ObjectRetrievalFailureException
     *          the object retrieval failure
     *          exception
     */
    private void assertEntityRetrieved(T entity, PK id) throws ObjectRetrievalFailureException {
        if (entity == null) {
            Log.error(this, "Entiteit [" + this.persistentClass.getSimpleName() + "] met id [" + id + "] niet gevonden.");
            throw new ObjectRetrievalFailureException(this.persistentClass, id);
        }
    }

    /**
     * Assert unique result size.
     *
     * @param result     the result
     * @param parmNames  the parm names
     * @param parmValues the parm values
     * @return the object
     * @throws org.springframework.dao.IncorrectResultSizeDataAccessException
     *          the incorrect result size
     *          data access exception
     * @throws org.springframework.orm.ObjectRetrievalFailureException
     *          the object retrieval failure
     *          exception
     */
    @SuppressWarnings("unused")
    protected Object assertUniqueResultSize(List<T> result, String[] parmNames, Object[] parmValues) throws IncorrectResultSizeDataAccessException, ObjectRetrievalFailureException {
        Object object = DataAccessUtils.uniqueResult(result);

        if (object == null) {
            StringBuilder message = new StringBuilder();
            message.append("Entiteit [");
            message.append(getPersistentClass().getSimpleName());
            message.append("] met keys [");
            for (String parmName : parmNames) {
                message.append(parmName);
                message.append(";");
            }

            message.append("] en keywaarden[");

            for (Object parmValue : parmValues) {
                message.append(parmValue.toString());
                message.append(";");
            }
            message.append("] werd niet gevonden.");
            Log.error(this, message.toString());
            //Log.error(this, "Entiteit [" + getPersistentClass().getSimpleName() + "] met keys [" + parmNames + "] en keywaarden[" + parmValues +  "] werd niet gevonden.");
            throw new ObjectRetrievalFailureException(getPersistentClass(), parmValues);
        }
        return object;
    }

    @Override
    public int getFirstRowNumber(final int currentPageNumber, final int pagingSize) {
        int firstRowNumber = 0;

        if (currentPageNumber > QUERY_PAGING_CURRENTPAGINGPAGENUMBER_DEFAULT) {
            /*
               E.g. currentPageNumber = 1 and pagingSize=500 => firstRowNumber = (1 - 1) * 500 = 0 * 500 = 0
               E.g. currentPageNumber = 2 and pagingSize=500 => firstRowNumber = (2 - 1) * 500 = 1 * 500 = 500
             */
            firstRowNumber = (currentPageNumber - 1) * pagingSize;
            //firstRowNumber = currentPageNumber * pagingSize;
        }

        if (firstRowNumber < 0) {
            throw new IllegalStateException("FirstRowNumber must be a positive number");
        }

        return firstRowNumber;
    }

    protected Class<T> getPersistentClass() {
        return this.persistentClass;
    }

    private void setPersistentClass(Class<T> persistentClass) {   // NOSONAR
        this.persistentClass = persistentClass;  // NOSONAR
    }
}
