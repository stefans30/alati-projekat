package com.stefansamardzija.alati_projekat.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Predstavlja telefon koji se prodaje.
 *
 * Sadrzi naziv, cenu i opis specifikacija telefona.
 *
 * @author Stefan Samardzija
 * @version 1.0
 */
@Entity
@Table(name = "telefon")
public class Telefon {

    /** Identifikator telefona u bazi podataka. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTelefon;

    /** Naziv telefona kao String. */
    @Column(nullable = false)
    private String naziv;

    /** Cena telefona kao BigDecimal. */
    @Column(nullable = false)
    private BigDecimal cena;

    /** Opis specifikacija telefona kao String. */
    @Column(length = 1000)
    private String specifikacije;

    /**
     * Prazan konstruktor potreban za JPA.
     */
    public Telefon() {
    }

    /**
     * Kreira novi telefon sa zadatim nazivom, cenom i specifikacijama.
     *
     * @param naziv Naziv telefona.
     * @param cena Cena telefona.
     * @param specifikacije Opis specifikacija telefona.
     */
    public Telefon(String naziv, BigDecimal cena, String specifikacije) {
        this.naziv = naziv;
        this.cena = cena;
        this.specifikacije = specifikacije;
    }

    /**
     * Vraca identifikator telefona.
     *
     * @return Identifikator telefona.
     */
    public Long getIdTelefon() {
        return idTelefon;
    }

    /**
     * Postavlja identifikator telefona na unetu vrednost.
     *
     * @param idTelefon Novi identifikator telefona.
     */
    public void setIdTelefon(Long idTelefon) {
        this.idTelefon = idTelefon;
    }

    /**
     * Vraca naziv telefona.
     *
     * @return Naziv telefona.
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Postavlja naziv telefona na unetu vrednost.
     *
     * @param naziv Novi naziv telefona.
     * @throws java.lang.NullPointerException Ako je naziv null.
     * @throws java.lang.IllegalArgumentException Ako naziv ima manje od 2 znaka.
     */
    public void setNaziv(String naziv) {
        if (naziv == null) throw new NullPointerException("Naziv ne sme biti null");
        if (naziv.length() < 2) throw new IllegalArgumentException("Naziv mora imati bar 2 znaka");
        this.naziv = naziv;
    }

    /**
     * Vraca cenu telefona.
     *
     * @return Cena telefona.
     */
    public BigDecimal getCena() {
        return cena;
    }

    /**
     * Postavlja cenu telefona na unetu vrednost.
     *
     * @param cena Nova cena telefona.
     * @throws java.lang.NullPointerException Ako je cena null.
     * @throws java.lang.IllegalArgumentException Ako cena nije veca od 0.
     */
    public void setCena(BigDecimal cena) {
        if (cena == null) throw new NullPointerException("Cena ne sme biti null");
        if (cena.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Cena mora biti veca od 0");
        this.cena = cena;
    }

    /**
     * Vraca opis specifikacija telefona.
     *
     * @return Opis specifikacija telefona.
     */
    public String getSpecifikacije() {
        return specifikacije;
    }

    /**
     * Postavlja opis specifikacija telefona na unetu vrednost.
     *
     * @param specifikacije Novi opis specifikacija telefona.
     */
    public void setSpecifikacije(String specifikacije) {
        this.specifikacije = specifikacije;
    }

    /**
     * Poredi dva telefona po nazivu i ceni.
     *
     * @param o Drugi objekat sa kojim se poredi.
     * @return
     * <ul>
     * <li><b>true</b> - ako su oba objekta klase Telefon sa istim nazivom i cenom
     * ili ako su na istoj adresi.</li>
     * <li><b>false</b> - ako je drugi objekat null, ako je druge klase ili
     * ako naziv ili cena nisu isti.</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Telefon t = (Telefon) o;
        return Objects.equals(naziv, t.naziv) &&
                Objects.equals(cena, t.cena);
    }

    /**
     * Racuna hash kod telefona na osnovu naziva i cene.
     *
     * @return Hash kod telefona.
     */
    @Override
    public int hashCode() {
        return Objects.hash(naziv, cena);
    }

    /**
     * Vraca tekstualnu reprezentaciju telefona.
     *
     * @return Tekstualna reprezentacija telefona.
     */
    @Override
    public String toString() {
        return "Telefon{naziv='" + naziv + "', cena=" + cena + "}";
    }
}
