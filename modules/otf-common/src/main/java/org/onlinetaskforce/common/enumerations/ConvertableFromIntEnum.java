package org.onlinetaskforce.common.enumerations;

/**
 * This interface marks an enumeration that contains codes that represent integers.
 * The codes that represent the enumerated values are for example something like '1', '2'...
 * An enumeration constant cannot start with a decimal number. Therefore, it is prefixed with something
 * like 'CODE_1', 'CODE_2',...
 * The toString method will be overriden to remove the prefix from the enumeration constant name so that the
 * correct code will be persisted ('1', '2'...) when the value of the enum is retrieved.
 *
 * To retrieve the correct enumeration constant based on a numeric String (or Integer) value cannot be retrieved
 * using for example <<EnumClass>>.valueOf("1"). This would only work with <<EnumClass>>.valueOf("CODE_1").
 * For retrieving the correct enumeration constant based on a numeric String (or Integer) value, the EnumMapper
 * can be used.
 * @param <E> The concrete enumeration class that implements this interface.
 *
 * User: jordens
 * Date: 11/05/13
 */
public interface ConvertableFromIntEnum<E extends Enum<E> & ConvertableFromIntEnum<E>> {

    /**
     * The prefix that is used for all numeric enumeration names in the concrete subclass of this interface.
     */
    String ENUM_CONSTANT_PREFIX = "CODE_";
}
