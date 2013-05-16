package org.onlinetaskforce.common.domain;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Type;
import org.onlinetaskforce.common.enumerations.TijdEnum;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**

 */
@Entity
@Table(name = "reservatie")
public class Reservatie extends AbstractAuditPojo {

    /**
     * Tijdstip van het moment waarop deze entiteit geannuleerd werd.
     */
    @Type(type = "org.onlinetaskforce.persistence.types.UtcTimestampType")
    @Column(name = "annulatieTijdstipUtc", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date annulatieTijdstip;
    /**
     * De identificatie van de gebruiker die deze entiteit heeft geannuleerd.
     */
    @Column(name = "annulatieGebruikerId", nullable = true)
    private String annulatieGebruikerId;

    /**
     * The reservation number of this Reservatie.
     */
    @Column(name = "reservatie_nummer", nullable = false, length = 50)
    private Long reservatieNummer;

    /**
     * The Wagen.
     */
    @OneToOne(targetEntity = Wagen.class, cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "wagen_id")
    private Wagen wagen;

    @Type(type = "org.onlinetaskforce.persistence.types.UtcTimestampType")
    @Column(name = "datum_begin", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date beginDatum;

    @Type(type = "org.onlinetaskforce.persistence.types.UtcTimestampType")
    @Column(name = "datum_tot", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date eindDatum;

    private transient TijdEnum starttijd;
    private transient TijdEnum eindtijd;
    private transient Boolean zoekMethodeAutomatisch;

    /**
     * The reason why this car is reserverd.
     */
    @Column(name = "doel", nullable = false, length = 500)
    private String doel;

    public Long getReservatieNummer() {
        return reservatieNummer;
    }

    public void setReservatieNummer(Long reservatieNummer) {
        this.reservatieNummer = reservatieNummer;
    }

    public Wagen getWagen() {
        return wagen;
    }

    public void setWagen(Wagen wagen) {
        this.wagen = wagen;
    }

    public Date getBeginDatum() {
        return beginDatum;
    }

    public void setBeginDatum(Date beginDatum) {
        this.beginDatum = beginDatum;
    }

    public Date getEindDatum() {
        return eindDatum;
    }

    public void setEindDatum(Date eindDatum) {
        this.eindDatum = eindDatum;
    }

    public String getDoel() {
        return doel;
    }

    public void setDoel(String doel) {
        this.doel = doel;
    }

    public TijdEnum getStarttijd() {
        return starttijd;
    }

    public void setStarttijd(TijdEnum starttijd) {
        this.starttijd = starttijd;
    }

    public TijdEnum getEindtijd() {
        return eindtijd;
    }

    public void setEindtijd(TijdEnum eindtijd) {
        this.eindtijd = eindtijd;
    }

    public Boolean getZoekMethodeAutomatisch() {
        return zoekMethodeAutomatisch;
    }

    public void setZoekMethodeAutomatisch(Boolean zoekMethodeAutomatisch) {
        this.zoekMethodeAutomatisch = zoekMethodeAutomatisch;
    }

    public Date getAnnulatieTijdstip() {
        return annulatieTijdstip;
    }

    public void setAnnulatieTijdstip(Date annulatieTijdstip) {
        this.annulatieTijdstip = annulatieTijdstip;
    }

    public String getAnnulatieGebruikerId() {
        return annulatieGebruikerId;
    }

    public void setAnnulatieGebruikerId(String annulatieGebruikerId) {
        this.annulatieGebruikerId = annulatieGebruikerId;
    }

    public boolean isAnnulatie() {
        return StringUtils.isNotBlank(annulatieGebruikerId) && annulatieTijdstip != null;
    }
}
