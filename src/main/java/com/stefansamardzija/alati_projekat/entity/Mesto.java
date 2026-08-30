package com.stefansamardzija.alati_projekat.entity;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * Predstavlja mesto iz kog kupac dolazi.
 *
 * @author Stefan Samardzija
 * @version 1.0
 */
@Entity
@Table(name = "mesto")
public class Mesto {

    /** Identifikator mesta u bazi podataka. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMesto;

    /** Naziv mesta kao String. */
    @Column(nullable = false)
    private String naziv;

    /**
     * Prazan konstruktor potreban za JPA.
     */
    public Mesto() {
    }

    /**
     * Kreira novo mesto sa zadatim nazivom.
     *
     * @param naziv Naziv mesta.
     */
    public Mesto(String naziv) {
        this.naziv = naziv;
    }

    /**
     * Vraca identifikator mesta.
     *
     * @return Identifikator mesta.
     */
    public Long getIdMesto() {
        return idMesto;
    }

    /**
     * Postavlja identifikator mesta na unetu vrednost.
     *
     * @param idMesto Novi identifikator mesta.
     */
    public void setIdMesto(Long idMesto) {
        this.idMesto = idMesto;
    }

    /**
     * Vraca naziv mesta.
     *
     * @return Naziv mesta.
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Postavlja naziv mesta na unetu vrednost.
     *
     * @param naziv Novi naziv mesta.
     * @throws java.lang.NullPointerException Ako je naziv null.
     * @throws java.lang.IllegalArgumentException Ako naziv ima manje od 2 znaka.
     */
    public void setNaziv(String naziv) {
        if (naziv == null) throw new NullPointerException("Naziv ne sme biti null");
        if (naziv.length() < 2) throw new IllegalArgumentException("Naziv mora imati bar 2 znaka");
        this.naziv = naziv;
    }

    /**
     * Poredi dva mesta po nazivu.
     *
     * @param o Drugi objekat sa kojim se poredi.
     * @return
     * <ul>
     * <li><b>true</b> - ako su oba objekta klase Mesto sa istim nazivom
     * ili ako su na istoj adresi.</li>
     * <li><b>false</b> - ako je drugi objekat null, ako je druge klase ili
     * ako naziv nije isti.</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mesto m = (Mesto) o;
        return Objects.equals(naziv, m.naziv);
    }

    /**
     * Racuna hash kod mesta na osnovu naziva.
     *
     * @return Hash kod mesta.
     */
    @Override
    public int hashCode() {
        return Objects.hash(naziv);
    }

    /**
     * Vraca tekstualnu reprezentaciju mesta.
     *
     * @return Tekstualna reprezentacija mesta.
     */
    @Override
    public String toString() {
        return "Mesto{naziv='" + naziv + "'}";
    }
}
