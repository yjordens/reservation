package org.onlinetaskforce.persistence.dao;

import org.onlinetaskforce.common.exceptions.BusinessException;

import java.io.Serializable;
import java.util.List;

/**
 * Generic DAO (Data Access Object) with common methods to CRUD POJOs.
 *
 * <p>Extend this interface if you want typesafe (no casting necessary) DAO's for your
 * domain objects.</p>
 *
 * @author jordens
 * @param <T>   The Class of the domain pojo that is persisted using the concrete implementation of this interface.
 * @param <PK>  The type of the Primary Key of the above domain pojo. (Eg String)
 */
public interface BaseDomainDao<T, PK extends Serializable> {
    /**
     * Generic method used to get all objects of a particular type. This
     * is the same as lookup up all rows in a table.
     * @return List of populated objects. An empty List if no results are found.
     */
    List<T> getAll();

    /**
     * Generic method to get an object based on class and identifier. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if
     * nothing is found.
     *
     * @param id the identifier (primary key) of the object to get
     * @return a populated object
     * @see org.springframework.orm.ObjectRetrievalFailureException
     */
    T get(PK id);

    /**
     * Generic method to load an object based on class and identifier.
     *
     * @param id the identifier (primary key) of the object to get
     * @return a populated object
     */
    T load(PK id);

    /**
     * Checks for existence of an object of type T using the id arg.
     * @param id The identifier of the object to check
     * @return - true if it exists, false if it doesn't
     */
    boolean exists(PK id);

    /**
     * Generic method to save an object - handles both update and insert.
     * @param object the object to save
     * @return  the saved object.
     */
    T saveOrUpdate(T object);

    /**
     *
     * @param object
     * @return
     */
    T merge(T object);

    /**
     * Generic method to save an object with the explicit handling of the possible constraints that are raised by the database - handles both update and insert.
     * In order to catch a possible constraint violation, the current session will be flushed during this method.
     * @param object the object to save
     * @return the saved object
     * @throws BusinessException In case of a specific constraint was violated in the database.
     */
    T saveOrUpdateAndCheckConstraints(T object) throws BusinessException;

    /**
     * Generic method to delete an object based on class and id
     * @param object the object to remove
     */
    void remove(T object);

    /**
     * Gets the first row number.
     *
     * @param currentPageNumber the current page number
     * @param pagingSize the paging size
     * @return the first row number
     */
    int getFirstRowNumber(final int currentPageNumber, final int pagingSize);
}
