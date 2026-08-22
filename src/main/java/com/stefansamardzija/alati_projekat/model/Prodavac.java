package com.stefansamardzija.alati_projekat.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Predstavlja prodavca koji zaključuje račune i poseduje sertifikate.
 */
@Entity
public class Prodavac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProdavac;

    private String ime;
    private String prezime;
    private String mejl;
    private String korisnickoIme;
    private String sifra;

    @OneToMany(mappedBy = "prodavac", cascade = CascadeType.ALL)
    private List<ProdavacSertifikat> sertifikati = new ArrayList<>();

    public Prodavac() {
    }

    public Prodavac(String ime, String prezime, String mejl, String korisnickoIme, String sifra) {
        this.ime = ime;
        this.prezime = prezime;
        this.mejl = mejl;
        this.korisnickoIme = korisnickoIme;
        this.sifra = sifra;
    }

    public Long getIdProdavac() {
        return idProdavac;
    }

    public void setIdProdavac(Long idProdavac) {
        this.idProdavac = idProdavac;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getMejl() {
        return mejl;
    }

    public void setMejl(String mejl) {
        this.mejl = mejl;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public List<ProdavacSertifikat> getSertifikati() {
        return sertifikati;
    }

    public void setSertifikati(List<ProdavacSertifikat> sertifikati) {
        this.sertifikati = sertifikati;
    }
}
