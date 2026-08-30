package com.stefansamardzija.alati_projekat.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Predstavlja prodavca telefona.
 *
 * Sadrzi licne podatke, podatke za prijavu na sistem i listu
 * sertifikata koje prodavac poseduje.
 *
 * @author Stefan Samardzija
 * @version 1.0
 */
@Entity
@Table(name = "prodavac")
public class Prodavac {

    /** Identifikator prodavca u bazi podataka. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProdavac;

    /** Ime prodavca kao String. */
    @Column(nullable = false)
    private String ime;

    /** Prezime prodavca kao String. */
    @Column(nullable = false)
    private String prezime;

    /** Mejl adresa prodavca kao String. */
    @Column(nullable = false)
    private String mejl;

    /** Korisnicko ime prodavca kao String. */
    @Column(nullable = false, unique = true)
    private String korisnickoIme;

    /** Sifra prodavca kao String. */
    @Column(nullable = false)
    private String sifra;

    /** Lista sertifikata koje prodavac poseduje. */
    @OneToMany(mappedBy = "prodavac", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("prodavac-sertifikati")
    private List<ProdavacSertifikat> sertifikati = new ArrayList<>();

    /**
     * Prazan konstruktor potreban za JPA.
     */
    public Prodavac() {
    }

    /**
     * Kreira novog prodavca sa zadatim podacima.
     *
     * @param ime Ime prodavca.
     * @param prezime Prezime prodavca.
     * @param mejl Mejl adresa prodavca.
     * @param korisnickoIme Korisnicko ime prodavca.
     * @param sifra Sifra prodavca.
     */
    public Prodavac(String ime, String prezime, String mejl, String korisnickoIme, String sifra) {
        this.ime = ime;
        this.prezime = prezime;
        this.mejl = mejl;
        this.korisnickoIme = korisnickoIme;
        this.sifra = sifra;
    }

    /**
     * Vraca identifikator prodavca.
     *
     * @return Identifikator prodavca.
     */
    public Long getIdProdavac() {
        return idProdavac;
    }

    /**
     * Postavlja identifikator prodavca na unetu vrednost.
     *
     * @param idProdavac Novi identifikator prodavca.
     */
    public void setIdProdavac(Long idProdavac) {
        this.idProdavac = idProdavac;
    }

    /**
     * Vraca ime prodavca.
     *
     * @return Ime prodavca.
     */
    public String getIme() {
        return ime;
    }

    /**
     * Postavlja ime prodavca na unetu vrednost.
     *
     * @param ime Novo ime prodavca.
     * @throws java.lang.NullPointerException Ako je ime null.
     * @throws java.lang.IllegalArgumentException Ako ime ima manje od 2 znaka.
     */
    public void setIme(String ime) {
        if (ime == null) throw new NullPointerException("Ime ne sme biti null");
        if (ime.length() < 2) throw new IllegalArgumentException("Ime mora imati bar 2 znaka");
        this.ime = ime;
    }

    /**
     * Vraca prezime prodavca.
     *
     * @return Prezime prodavca.
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * Postavlja prezime prodavca na unetu vrednost.
     *
     * @param prezime Novo prezime prodavca.
     * @throws java.lang.NullPointerException Ako je prezime null.
     * @throws java.lang.IllegalArgumentException Ako prezime ima manje od 2 znaka.
     */
    public void setPrezime(String prezime) {
        if (prezime == null) throw new NullPointerException("Prezime ne sme biti null");
        if (prezime.length() < 2) throw new IllegalArgumentException("Prezime mora imati bar 2 znaka");
        this.prezime = prezime;
    }

    /**
     * Vraca mejl adresu prodavca.
     *
     * @return Mejl adresa prodavca.
     */
    public String getMejl() {
        return mejl;
    }

    /**
     * Postavlja mejl adresu prodavca na unetu vrednost.
     *
     * @param mejl Nova mejl adresa prodavca.
     * @throws java.lang.NullPointerException Ako je mejl null.
     * @throws java.lang.IllegalArgumentException Ako mejl ne sadrzi karakter '@'.
     */
    public void setMejl(String mejl) {
        if (mejl == null) throw new NullPointerException("Mejl ne sme biti null");
        if (!mejl.contains("@")) throw new IllegalArgumentException("Mejl mora sadrzati @");
        this.mejl = mejl;
    }

    /**
     * Vraca korisnicko ime prodavca.
     *
     * @return Korisnicko ime prodavca.
     */
    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    /**
     * Postavlja korisnicko ime prodavca na unetu vrednost.
     *
     * @param korisnickoIme Novo korisnicko ime prodavca.
     * @throws java.lang.NullPointerException Ako je korisnicko ime null.
     * @throws java.lang.IllegalArgumentException Ako korisnicko ime ima manje od 4 znaka.
     */
    public void setKorisnickoIme(String korisnickoIme) {
        if (korisnickoIme == null) throw new NullPointerException("Korisnicko ime ne sme biti null");
        if (korisnickoIme.length() < 4) throw new IllegalArgumentException("Korisnicko ime mora imati bar 4 znaka");
        this.korisnickoIme = korisnickoIme;
    }

    /**
     * Vraca sifru prodavca.
     *
     * @return Sifra prodavca.
     */
    public String getSifra() {
        return sifra;
    }

    /**
     * Postavlja sifru prodavca na unetu vrednost.
     *
     * @param sifra Nova sifra prodavca.
     * @throws java.lang.NullPointerException Ako je sifra null.
     * @throws java.lang.IllegalArgumentException Ako sifra ima manje od 6 znakova.
     */
    public void setSifra(String sifra) {
        if (sifra == null) throw new NullPointerException("Sifra ne sme biti null");
        if (sifra.length() < 6) throw new IllegalArgumentException("Sifra mora imati bar 6 znakova");
        this.sifra = sifra;
    }

    /**
     * Vraca listu sertifikata koje prodavac poseduje.
     *
     * @return Lista sertifikata prodavca.
     */
    public List<ProdavacSertifikat> getSertifikati() {
        return sertifikati;
    }

    /**
     * Postavlja listu sertifikata prodavca na unetu vrednost.
     *
     * @param sertifikati Nova lista sertifikata prodavca.
     */
    public void setSertifikati(List<ProdavacSertifikat> sertifikati) {
        this.sertifikati = sertifikati;
    }

    /**
     * Poredi dva prodavca po korisnickom imenu, imenu i prezimenu.
     *
     * @param o Drugi objekat sa kojim se poredi.
     * @return
     * <ul>
     * <li><b>true</b> - ako su oba objekta klase Prodavac sa istim korisnickim
     * imenom, imenom i prezimenom ili ako su na istoj adresi.</li>
     * <li><b>false</b> - ako je drugi objekat null, ako je druge klase ili
     * ako se neko od navedenih polja razlikuje.</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prodavac p = (Prodavac) o;
        return Objects.equals(korisnickoIme, p.korisnickoIme) &&
                Objects.equals(ime, p.ime) &&
                Objects.equals(prezime, p.prezime);
    }

    /**
     * Racuna hash kod prodavca na osnovu korisnickog imena, imena i prezimena.
     *
     * @return Hash kod prodavca.
     */
    @Override
    public int hashCode() {
        return Objects.hash(korisnickoIme, ime, prezime);
    }

    /**
     * Vraca tekstualnu reprezentaciju prodavca.
     *
     * @return Tekstualna reprezentacija prodavca.
     */
    @Override
    public String toString() {
        return "Prodavac{korisnickoIme='" + korisnickoIme + "', ime='" + ime + "', prezime='" + prezime + "'}";
    }
}
