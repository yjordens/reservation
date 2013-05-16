package org.onlinetaskforce.common.dto;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * @author jordens
 * @since 22/04/13
 */
public class ZoekWagenDto implements Serializable{
    private String nummerplaat;
    private String merk;
    private String merktype;
    private Boolean actief;

    public String getNummerplaat() {
        return nummerplaat;
    }

    public boolean isEmpty() {
        if (StringUtils.isNotBlank(nummerplaat)) {
            return false;
        } else if (StringUtils.isNotBlank(merk)) {
           return false;
        } else if (StringUtils.isNotBlank(merktype)) {
           return false;
        } else if (actief != null) {
           return false;
        }
        return true;
    }

    public void setNummerplaat(String nummerplaat) {
        this.nummerplaat = nummerplaat;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getMerktype() {
        return merktype;
    }

    public void setMerktype(String merktype) {
        this.merktype = merktype;
    }

    public Boolean getActief() {
        return actief;
    }

    public void setActief(Boolean actief) {
        this.actief = actief;
    }
}
