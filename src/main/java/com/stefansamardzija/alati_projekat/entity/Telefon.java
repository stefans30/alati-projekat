package com.stefansamardzija.alati_projekat.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "telefon")
public class Telefon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTelefon;

    @Column(nullable = false)
    private String naziv;

    @Column(nullable = false)
    private BigDecimal cena;

    @Column(length = 1000)
    private String specifikacije;

    public Telefon() {
    }

    public Telefon(String naziv, BigDecimal cena, String specifikacije) {
        this.naziv = naziv;
        this.cena = cena;
        this.specifikacije = specifikacije;
    }

    public Long getIdTelefon() {
        return idTelefon;
    }

    public void setIdTelefon(Long idTelefon) {
        this.idTelefon = idTelefon;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        if (naziv == null) throw new NullPointerException("Naziv ne sme biti null");
        if (naziv.length() < 2) throw new IllegalArgumentException("Naziv mora imati bar 2 znaka");
        this.naziv = naziv;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        if (cena == null) throw new NullPointerException("Cena ne sme biti null");
        if (cena.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Cena mora biti veca od 0");
        this.cena = cena;
    }

    public String getSpecifikacije() {
        return specifikacije;
    }

    public void setSpecifikacije(String specifikacije) {
        this.specifikacije = specifikacije;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Telefon t = (Telefon) o;
        return Objects.equals(naziv, t.naziv) &&
                Objects.equals(cena, t.cena);
    }

    @Override
    public int hashCode() {
        return Objects.hash(naziv, cena);
    }

    @Override
    public String toString() {
        return "Telefon{naziv='" + naziv + "', cena=" + cena + "}";
    }
}
