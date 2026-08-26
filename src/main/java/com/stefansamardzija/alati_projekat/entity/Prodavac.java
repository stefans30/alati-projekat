package com.stefansamardzija.alati_projekat.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "prodavac")
public class Prodavac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProdavac;

    @Column(nullable = false)
    private String ime;

    @Column(nullable = false)
    private String prezime;

    @Column(nullable = false)
    private String mejl;

    @Column(nullable = false, unique = true)
    private String korisnickoIme;

    @Column(nullable = false)
    private String sifra;

    @OneToMany(mappedBy = "prodavac", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("prodavac-sertifikati")
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
        if (ime == null) throw new NullPointerException("Ime ne sme biti null");
        if (ime.length() < 2) throw new IllegalArgumentException("Ime mora imati bar 2 znaka");
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        if (prezime == null) throw new NullPointerException("Prezime ne sme biti null");
        if (prezime.length() < 2) throw new IllegalArgumentException("Prezime mora imati bar 2 znaka");
        this.prezime = prezime;
    }

    public String getMejl() {
        return mejl;
    }

    public void setMejl(String mejl) {
        if (mejl == null) throw new NullPointerException("Mejl ne sme biti null");
        if (!mejl.contains("@")) throw new IllegalArgumentException("Mejl mora sadrzati @");
        this.mejl = mejl;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        if (korisnickoIme == null) throw new NullPointerException("Korisnicko ime ne sme biti null");
        if (korisnickoIme.length() < 4) throw new IllegalArgumentException("Korisnicko ime mora imati bar 4 znaka");
        this.korisnickoIme = korisnickoIme;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        if (sifra == null) throw new NullPointerException("Sifra ne sme biti null");
        if (sifra.length() < 6) throw new IllegalArgumentException("Sifra mora imati bar 6 znakova");
        this.sifra = sifra;
    }

    public List<ProdavacSertifikat> getSertifikati() {
        return sertifikati;
    }

    public void setSertifikati(List<ProdavacSertifikat> sertifikati) {
        this.sertifikati = sertifikati;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prodavac p = (Prodavac) o;
        return Objects.equals(korisnickoIme, p.korisnickoIme) &&
                Objects.equals(ime, p.ime) &&
                Objects.equals(prezime, p.prezime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(korisnickoIme, ime, prezime);
    }

    @Override
    public String toString() {
        return "Prodavac{korisnickoIme='" + korisnickoIme + "', ime='" + ime + "', prezime='" + prezime + "'}";
    }
}
