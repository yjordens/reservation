package org.onlinetaskforce.common.domain;

import org.hibernate.annotations.Type;
import org.onlinetaskforce.common.enumerations.TijdEnum;
import org.onlinetaskforce.common.utils.DateTimeUtil;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**

 */
@Entity
@Table(name = "wagen_ontvangst")
public class WagenOntvangst extends AbstractAuditPojo{

    /**
     * The Gebruiker that owns the reservation.
     */
    @OneToOne(targetEntity = Gebruiker.class, cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "reserveerder_id")
    private Gebruiker reserveerder;

    /**
     * The remark.
     */
    @Column(name = "opmerking", nullable = true, length = 500)
    private String opmerking;

    @Type(type = "org.onlinetaskforce.persistence.types.UtcTimestampType")
    @Column(name = "ontvangsttijdstiputc", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ontvangstTijdstip;

    private transient TijdEnum ontvangsttijd;
    private transient Long kilometerStand;

    public Gebruiker getReserveerder() {
        return reserveerder;
    }

    public void setReserveerder(Gebruiker reserveerder) {
        this.reserveerder = reserveerder;
    }

    public String getOpmerking() {
        return opmerking;
    }

    public void setOpmerking(String opmerking) {
        this.opmerking = opmerking;
    }

    public Date getOntvangstTijdstip() {
        return ontvangstTijdstip;
    }

    public void setOntvangstTijdstip(Date ontvangstTijdstip) {
        this.ontvangstTijdstip = ontvangstTijdstip;
    }

    public TijdEnum getOntvangsttijd() {
        return ontvangsttijd;
    }

    public void setOntvangsttijd(TijdEnum ontvangsttijd) {
        this.ontvangsttijd = ontvangsttijd;
    }

    public Long getKilometerStand() {
        return kilometerStand;
    }

    public void setKilometerStand(Long kilometerStand) {
        this.kilometerStand = kilometerStand;
    }

    /**
     * Zet de tijd op de date
     */
    public void defineOntvangstTijdstip() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(getOntvangstTijdstip());
        calendar.set(Calendar.HOUR, DateTimeUtil.getUur(ontvangsttijd));
        calendar.set(Calendar.MINUTE, DateTimeUtil.getMinuten(ontvangsttijd));
        ontvangstTijdstip = calendar.getTime();
    }

    public void determineTijden() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(getOntvangstTijdstip());
        ontvangsttijd = DateTimeUtil.getTijdEnum(calendar);
    }
}
