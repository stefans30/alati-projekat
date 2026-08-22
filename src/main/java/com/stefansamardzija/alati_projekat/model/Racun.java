package com.stefansamardzija.alati_projekat.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Racun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRacun;

    private LocalDate datum;
    private double ukupanIznos;

    @ManyToOne
    @JoinColumn(name = "idProdavac")
    private Prodavac prodavac;

    @ManyToOne
    @JoinColumn(name = "idKupac")
    private Kupac kupac;

    @OneToMany(mappedBy = "racun", cascade = CascadeType.ALL)
    private List<StavkaRacuna> stavke = new ArrayList<>();

    public Racun() {
    }

    public Racun(LocalDate datum, double ukupanIznos, Prodavac prodavac, Kupac kupac) {
        this.datum = datum;
        this.ukupanIznos = ukupanIznos;
        this.prodavac = prodavac;
        this.kupac = kupac;
    }

    public Long getIdRacun() {
        return idRacun;
    }

    public void setIdRacun(Long idRacun) {
        this.idRacun = idRacun;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public double getUkupanIznos() {
        return ukupanIznos;
    }

    public void setUkupanIznos(double ukupanIznos) {
        this.ukupanIznos = ukupanIznos;
    }

    public Prodavac getProdavac() {
        return prodavac;
    }

    public void setProdavac(Prodavac prodavac) {
        this.prodavac = prodavac;
    }

    public Kupac getKupac() {
        return kupac;
    }

    public void setKupac(Kupac kupac) {
        this.kupac = kupac;
    }

    public List<StavkaRacuna> getStavke() {
        return stavke;
    }

    public void setStavke(List<StavkaRacuna> stavke) {
        this.stavke = stavke;
    }
}
