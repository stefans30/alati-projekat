package com.stefansamardzija.alati_projekat.model;

import jakarta.persistence.Entity;

@Entity
public class PravnoLice extends Kupac {

    private String nazivFirme;
    private String pib;

    public PravnoLice() {
    }

    public PravnoLice(String mejl, Mesto mesto, String nazivFirme, String pib) {
        super(mejl, mesto);
        this.nazivFirme = nazivFirme;
        this.pib = pib;
    }

    public String getNazivFirme() {
        return nazivFirme;
    }

    public void setNazivFirme(String nazivFirme) {
        this.nazivFirme = nazivFirme;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }
}
