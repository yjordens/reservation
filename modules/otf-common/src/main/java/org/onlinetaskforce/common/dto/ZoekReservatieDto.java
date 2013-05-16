package org.onlinetaskforce.common.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * @author jordens
 * @since 22/04/13
 */
public class ZoekReservatieDto implements Serializable{
    private String gebruikersnaam;
    private Long reservatieNummer;
    private Boolean overtijd;
    private String nummerplaat;
    private String wagenId;
    private Date start;
    private Date end;
    private Date open;

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    public Long getReservatieNummer() {
        return reservatieNummer;
    }

    public void setReservatieNummer(Long reservatieNummer) {
        this.reservatieNummer = reservatieNummer;
    }

    public Boolean getOvertijd() {
        return overtijd;
    }

    public void setOvertijd(Boolean overtijd) {
        this.overtijd = overtijd;
    }

    public String getNummerplaat() {
        return nummerplaat;
    }

    public void setNummerplaat(String nummerplaat) {
        this.nummerplaat = nummerplaat;
    }

    public String getWagenId() {
        return wagenId;
    }

    public void setWagenId(String wagenId) {
        this.wagenId = wagenId;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getOpen() {
        return open;
    }

    public void setOpen(Date open) {
        this.open = open;
    }
}
