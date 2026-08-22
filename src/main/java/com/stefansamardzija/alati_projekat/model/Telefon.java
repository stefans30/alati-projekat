package com.stefansamardzija.alati_projekat.model;

import jakarta.persistence.*;

@Entity
public class Telefon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTelefon;

    private double cena;
    private String naziv;
    private String specifikacije;

    public Telefon() {
    }

    public Telefon(double cena, String naziv, String specifikacije) {
        this.cena = cena;
        this.naziv = naziv;
        this.specifikacije = specifikacije;
    }

    public Long getIdTelefon() {
        return idTelefon;
    }

    public void setIdTelefon(Long idTelefon) {
        this.idTelefon = idTelefon;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getSpecifikacije() {
        return specifikacije;
    }

    public void setSpecifikacije(String specifikacije) {
        this.specifikacije = specifikacije;
    }
}
