package com.stefansamardzija.alati_projekat.model;

import jakarta.persistence.*;

@Entity
public class StavkaRacuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStavkaRacuna;

    private int kolicina;
    private double cena;
    private double iznos;

    @ManyToOne
    @JoinColumn(name = "idRacun")
    private Racun racun;

    @ManyToOne
    @JoinColumn(name = "idTelefon")
    private Telefon telefon;

    public StavkaRacuna() {
    }

    public StavkaRacuna(int kolicina, double cena, double iznos, Racun racun, Telefon telefon) {
        this.kolicina = kolicina;
        this.cena = cena;
        this.iznos = iznos;
        this.racun = racun;
        this.telefon = telefon;
    }

    public Long getIdStavkaRacuna() {
        return idStavkaRacuna;
    }

    public void setIdStavkaRacuna(Long idStavkaRacuna) {
        this.idStavkaRacuna = idStavkaRacuna;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public double getIznos() {
        return iznos;
    }

    public void setIznos(double iznos) {
        this.iznos = iznos;
    }

    public Racun getRacun() {
        return racun;
    }

    public void setRacun(Racun racun) {
        this.racun = racun;
    }

    public Telefon getTelefon() {
        return telefon;
    }

    public void setTelefon(Telefon telefon) {
        this.telefon = telefon;
    }
}
