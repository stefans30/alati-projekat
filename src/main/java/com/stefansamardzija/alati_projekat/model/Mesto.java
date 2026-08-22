package com.stefansamardzija.alati_projekat.model;

import jakarta.persistence.*;

@Entity
public class Mesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMesto;

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
        this.naziv = naziv;
    }
}
