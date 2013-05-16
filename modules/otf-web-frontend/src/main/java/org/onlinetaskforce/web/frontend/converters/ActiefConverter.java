package org.onlinetaskforce.web.frontend.converters;

import org.apache.wicket.util.convert.IConverter;

import java.util.Locale;

/**
 * @author jordens
 * @since 26/04/13
 */
public class ActiefConverter<C> implements IConverter<Boolean> {

    @Override
    public Boolean convertToObject(String value, Locale locale) {
        if ("TRUE".equalsIgnoreCase(value)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public String convertToString(Boolean value, Locale locale) {
        if (value) {
            return "Ja";
        } else {
            return "Nee";
        }
    }
}
