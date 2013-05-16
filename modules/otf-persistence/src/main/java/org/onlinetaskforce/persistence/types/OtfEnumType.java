package org.onlinetaskforce.persistence.types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.TypeResolver;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import org.onlinetaskforce.common.enumerations.EnumMapper;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * This UserType is used to map Enums to Strings and back while working with Hibernate, making it possible to store numeric Enums;
 * normally Enums can't start with a number, so in Discimus we prefix them. We still want them without the prefix in the database though,
 * so we map them here.
 * This UserType can be used for all Enums, not only numeric ones.
 *
 * It's mapped like this:
 *
 * @ Type( type = "org.onlinetaskforce.persistence.types.OtfEnumType",
 *        parameters = { @ Parameter(name = "enumClass", value = "<i>be.vlaanderen.ov.discimus.common.enums.SomeEnum</i>")})
 */
public class OtfEnumType implements UserType, ParameterizedType {
    private Class<? extends Enum> enumClass;
    private Enum[] values;
    private AbstractSingleColumnStandardBasicType type = (AbstractSingleColumnStandardBasicType) (new TypeResolver()).basic(String.class.getName());

    @Override
    public void setParameterValues(Properties parameters) {
        String enumClassName = parameters.getProperty("enumClass");
        try {
            enumClass = Class.forName(enumClassName).asSubclass(Enum.class);
        } catch (ClassNotFoundException exception) {
            throw new HibernateException("Enum class not found", exception);
        }

        Method valuesMethod = null;
        try {
            valuesMethod = enumClass.getMethod("values", new Class[0]);
        } catch (Exception exception) {
            throw new HibernateException("Failed to optain identifier method", exception);
        }
        try {
            values = (Enum[]) valuesMethod.invoke(enumClass);
        } catch (Exception exception) {
            throw new HibernateException("Exception while invoking valueOfMethod of enumeration class: ", exception);
        }
    }

    @Override
    public Class returnedClass() {
        return enumClass;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        String identifier = (String) type.get(rs, names[0], session);
        return EnumMapper.valueOf(values, identifier);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        try {
            st.setObject(index, value != null ? value.toString() : null);
        } catch (Exception exception) {
            throw new HibernateException("Exception while invoking identifierMethod of enumeration class: ", exception);
        }
    }

    @Override
    public int[] sqlTypes() { // We only send Strings.
        return new int[]{type.sqlType()};
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {  //NOSONAR
        return x == null ? (y == null) : x.equals(y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}