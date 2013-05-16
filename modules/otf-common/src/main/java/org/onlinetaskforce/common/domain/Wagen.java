package org.onlinetaskforce.common.domain;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.onlinetaskforce.common.enumerations.BrandstofEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**

 */
@Entity
@Table(name = "wagen")
public class Wagen extends AbstractAuditPojo{

    /**
     * The nummerplaat of this Wagen.
     */
    @Column(name = "nummerplaat", nullable = true, length = 50)
    private String nummerplaat;

    /**
     * The merk of this Wagen.
     */
    @Column(name = "merk", nullable = false, length = 50)
    private String merk;

    /**
     * The merktype of this Wagen.
     */
    @Column(name = "merktype", nullable = false, length = 50)
    private String merktype;

    /**
     * The brandstof of this Wagen.
     */
    @Column(name = "brandstof", updatable = true, nullable = false)
    @Type(type = "org.onlinetaskforce.persistence.types.OtfEnumType",
            parameters = {@Parameter(name = "enumClass", value = "org.onlinetaskforce.common.enumerations.BrandstofEnum")})
    private BrandstofEnum brandstof;

    /**
     * Indicates if this current Wagen is still an active wagen.
     */
    @Column(name = "actief", nullable = false)
    private Boolean actief;

    public String getNummerplaat() {
        return nummerplaat;
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

    public BrandstofEnum getBrandstof() {
        return brandstof;
    }

    public void setBrandstof(BrandstofEnum brandstof) {
        this.brandstof = brandstof;
    }
}
