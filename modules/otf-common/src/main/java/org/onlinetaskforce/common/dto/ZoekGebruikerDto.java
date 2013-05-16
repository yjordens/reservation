package org.onlinetaskforce.common.dto;

import java.io.Serializable;

/**
 * @author jordens
 * @since 22/04/13
 */
public class ZoekGebruikerDto implements Serializable{
    private String gebruikersnaam;
    private String naam;
    private String voornaam;
    private Boolean actief;

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public Boolean getActief() {
        return actief;
    }

    public void setActief(Boolean actief) {
        this.actief = actief;
    }
}
