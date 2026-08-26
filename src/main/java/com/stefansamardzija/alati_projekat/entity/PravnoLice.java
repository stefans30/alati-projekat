package com.stefansamardzija.alati_projekat.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Objects;

@Entity
@DiscriminatorValue("PRAVNO")
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

    @Override
    public String getTip() {
        return "pravno";
    }

    public String getNazivFirme() {
        return nazivFirme;
    }

    public void setNazivFirme(String nazivFirme) {
        if (nazivFirme == null) throw new NullPointerException("Naziv firme ne sme biti null");
        if (nazivFirme.length() < 2) throw new IllegalArgumentException("Naziv firme mora imati bar 2 znaka");
        this.nazivFirme = nazivFirme;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        if (pib == null) throw new NullPointerException("PIB ne sme biti null");
        if (!pib.matches("\\d{9}")) throw new IllegalArgumentException("PIB mora imati tacno 9 cifara");
        this.pib = pib;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PravnoLice pl = (PravnoLice) o;
        return Objects.equals(pib, pl.pib) &&
                Objects.equals(nazivFirme, pl.nazivFirme) &&
                Objects.equals(getMejl(), pl.getMejl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(pib, nazivFirme, getMejl());
    }

    @Override
    public String toString() {
        return "PravnoLice{pib='" + pib + "', nazivFirme='" + nazivFirme +
                "', mejl='" + getMejl() + "'}";
    }
}
