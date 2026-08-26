package com.stefansamardzija.alati_projekat.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "mesto")
public class Mesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMesto;

    @Column(nullable = false)
    private String naziv;

    public Mesto() {
    }

    public Mesto(String naziv) {
        this.naziv = naziv;
    }

    public Long getIdMesto() {
        return idMesto;
    }

    public void setIdMesto(Long idMesto) {
        this.idMesto = idMesto;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        if (naziv == null) throw new NullPointerException("Naziv ne sme biti null");
        if (naziv.length() < 2) throw new IllegalArgumentException("Naziv mora imati bar 2 znaka");
        this.naziv = naziv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mesto m = (Mesto) o;
        return Objects.equals(naziv, m.naziv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(naziv);
    }

    @Override
    public String toString() {
        return "Mesto{naziv='" + naziv + "'}";
    }
}
