package com.stefansamardzija.alati_projekat.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Kupac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idKupac;

    private String mejl;

    @ManyToOne
    @JoinColumn(name = "idMesto")
    private Mesto mesto;

    public Kupac() {
    }

    public Kupac(String mejl, Mesto mesto) {
        this.mejl = mejl;
        this.mesto = mesto;
    }

    public Long getIdKupac() {
        return idKupac;
    }

    public void setIdKupac(Long idKupac) {
        this.idKupac = idKupac;
    }

    public String getMejl() {
        return mejl;
    }

    public void setMejl(String mejl) {
        this.mejl = mejl;
    }

    public Mesto getMesto() {
        return mesto;
    }

    public void setMesto(Mesto mesto) {
        this.mesto = mesto;
    }
}
