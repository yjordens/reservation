package org.onlinetaskforce.persistence.types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Hibernate User Type used to save timestamp data in the UTC timezone
 *
 * @author vanlooni
 * @since 4/01/2012
 */
public class UtcTimestampType implements UserType {

    /**
     * the SQL type this type manages
     */
    private static final int[] SQL_TYPES_UTC = {Types.TIMESTAMP};

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES_UTC;
    }

    @Override
    public boolean equals(Object x, Object y) {       //NOSONAR
        return (x == null) ? (y == null) : x.equals(y);
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Class returnedClass() {
        return objectClass;
    }

    /**
     * The class of objects returned by <code>nullSafeGet</code>. Currently,
     * returned objects are derived from this class, not exactly this class.
     */
    private Class objectClass = Date.class;

    @Override
    public int hashCode(Object x) {
        return x.hashCode();
    }

    /**
     * Transform the object into its cacheable representation. At the very least this
     * method should perform a deep copy if the type is mutable. That may not be enough
     * for some implementations, however; for example, associations must be cached as
     * identifier values. (optional operation)
     * throws HibernateException
     *
     * @param value the object to be cached
     * @return a cachable representation of the object
     */
    public Serializable disassemble(Object value) {
        return (Serializable) deepCopy(value);
    }

    /**
     * Reconstruct an object from the cacheable representation. At the very least this
     * method should perform a deep copy if the type is mutable. (optional operation)
     * throws HibernateException
     *
     * @param cached the object to be cached
     * @param owner  the owner of the cached object
     * @return a reconstructed object from the cachable representation
     */
    public Object assemble(Serializable cached, Object owner) {
        return deepCopy(cached);
    }

    /**
     * During merge, replace the existing (target) value in the entity we are merging to
     * with a new (original) value from the detached entity we are merging. For immutable
     * objects, or null values, it is safe to simply return the first parameter. For
     * mutable objects, it is safe to return a copy of the first parameter. For objects
     * with component values, it might make sense to recursively replace component values.
     * throws HibernateException
     *
     * @param original the value from the detached entity being merged
     * @param target   the value in the managed entity
     * @param owner    the owner of the cached object
     * @return the value to be merged
     */
    public Object replace(Object original, Object target, Object owner) {
        return deepCopy(original);
    }

    @Override
    public Object deepCopy(Object value) {
        return (value == null) ? null : new Date(((Date) value).getTime());
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SessionImplementor sessionImplementor, Object o) throws HibernateException, SQLException {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        Timestamp timestamp = resultSet.getTimestamp(strings[0], cal);
        return timestamp == null ? null : new Date(timestamp.getTime());
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SessionImplementor sessionImplementor) throws HibernateException, SQLException {
        if (!(o instanceof Date)) {
            o = deepCopy(o);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        if(o == null) {
            preparedStatement.setTimestamp(i, null, cal);
        } else {
            preparedStatement.setTimestamp(i, new Timestamp(((Date) o).getTime()), cal);
        }
    }
}
 