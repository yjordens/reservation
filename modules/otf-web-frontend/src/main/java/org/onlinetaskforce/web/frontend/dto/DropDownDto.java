package org.onlinetaskforce.web.frontend.dto;

import java.io.Serializable;

/**
 * @author jordens
 * @since 8/03/13
 */
public class DropDownDto implements Serializable {

    private String value;
    private String display;

    /**
     * Default constructor
     */
    public DropDownDto() {
    }

    /**
     * Constructor
     *
     * @param value   Property
     * @param display Property
     */
    public DropDownDto(String value, String display) {
        this.value = value;
        this.display = display;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return "DropDownDto{" +
                "value='" + value + '\'' +
                ", display='" + display + '\'' +
                '}';
    }
}
