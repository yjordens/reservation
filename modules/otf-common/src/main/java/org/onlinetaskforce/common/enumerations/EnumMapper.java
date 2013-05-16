package org.onlinetaskforce.common.enumerations;

import org.onlinetaskforce.common.exceptions.SystemException;

/**
 * This mapper maps a given value to its corresponding enumeration constant. Some enumerations actually map to a String that
 * correspond to an integer value (and possibly some leading zeroes). See the javadoc of ConvertableFromIntEnum for more information
 * about this strategy.
 *
 * This helper class makes it possible to map such a value to its correct enumeration constant without having to know the prefix
 * that is used to express the enumeration constant names.
 * Eg. Following expressinos all map a given value to the enumeration constant SituatieNaUitschrijvingEnum.CODE_1:
 * EnumMapper.valueOf(SituatieNaUitschrijvingEnum.values(), 1));
 * EnumMapper.valueOf(SituatieNaUitschrijvingEnum.values(), new Integer(1)));
 * EnumMapper.valueOf(SituatieNaUitschrijvingEnum.values(), "01"));
 * EnumMapper.valueOf(SituatieNaUitschrijvingEnum.values(), "1"));
 *
 * that may contain
 * User: jordens
 * Date: 11/05/13

 */
public final class EnumMapper {

    private EnumMapper() {
    }

    /**
     * Convert the given value to the corresponding enum constant of the given values.
     * @param values The list of possible values.
     * @param value The value to look for.
     * @param <E> The concrete enumeration type.
     * @return The corresponding enum constant. Throws a SystemException if no match is found.
     */
    public static <E extends Enum> E valueOf(E[] values, Integer value) {
        String stringValue = value.toString();
        return EnumMapper.valueOf(values, stringValue);
    }

    /**
     * Convert the given value to the corresponding enum constant of the given values.
     * @param values The list of possible values.
     * @param value The value to look for.
     * @param <E> The concrete enumeration type.
     * @return The corresponding enum constant. Throws a SystemException if no match is found.
     */
    public static <E extends Enum> E valueOf(E[] values, String value) {
        if (values == null || values.length ==0 || value == null) {
            return null;
        }
        String searchValue = value.trim();
        if (values[0] instanceof ConvertableFromIntEnum) {
            searchValue = searchValue.replaceFirst("^0+(?!$)", "");//strip alle leading zeros maar behoud minstens 1 karakter
            //searchValue = StringUtils.stripStart(searchValue, "0");
        }
        for (E enumValue : values) {
            if (enumValue.toString().equals(searchValue)) {
                return enumValue;
            }
        }
        throw new SystemException("No enum const found for value '" + value + "' in " + values[0].getClass());
    }
}
