package com.stefansamardzija.alati_projekat.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Objects;

/**
 * Predstavlja kupca koji je pravno lice.
 *
 * Nasledjuje zajednicke podatke iz klase Kupac i dodaje podatke
 * specificne za pravno lice (naziv firme i PIB).
 *
 * @author Stefan Samardzija
 * @version 1.0
 */
@Entity
@DiscriminatorValue("PRAVNO")
public class PravnoLice extends Kupac {

    /** Naziv firme pravnog lica kao String. */
    private String nazivFirme;

    /** PIB pravnog lica kao String. */
    private String pib;

    /**
     * Prazan konstruktor potreban za JPA.
     */
    public PravnoLice() {
    }

    /**
     * Kreira novo pravno lice sa zadatim podacima.
     *
     * @param mejl Mejl adresa kupca.
     * @param mesto Mesto iz kog kupac dolazi.
     * @param nazivFirme Naziv firme pravnog lica.
     * @param pib PIB pravnog lica.
     */
    public PravnoLice(String mejl, Mesto mesto, String nazivFirme, String pib) {
        super(mejl, mesto);
        this.nazivFirme = nazivFirme;
        this.pib = pib;
    }

    /**
     * Vraca oznaku tipa kupca za pravno lice.
     *
     * @return Uvek vraca "pravno".
     */
    @Override
    public String getTip() {
        return "pravno";
    }

    /**
     * Vraca naziv firme pravnog lica.
     *
     * @return Naziv firme pravnog lica.
     */
    public String getNazivFirme() {
        return nazivFirme;
    }

    /**
     * Postavlja naziv firme pravnog lica na unetu vrednost.
     *
     * @param nazivFirme Novi naziv firme pravnog lica.
     * @throws java.lang.NullPointerException Ako je naziv firme null.
     * @throws java.lang.IllegalArgumentException Ako naziv firme ima manje od 2 znaka.
     */
    public void setNazivFirme(String nazivFirme) {
        if (nazivFirme == null) throw new NullPointerException("Naziv firme ne sme biti null");
        if (nazivFirme.length() < 2) throw new IllegalArgumentException("Naziv firme mora imati bar 2 znaka");
        this.nazivFirme = nazivFirme;
    }

    /**
     * Vraca PIB pravnog lica.
     *
     * @return PIB pravnog lica.
     */
    public String getPib() {
        return pib;
    }

    /**
     * Postavlja PIB pravnog lica na unetu vrednost.
     *
     * @param pib Novi PIB pravnog lica.
     * @throws java.lang.NullPointerException Ako je pib null.
     * @throws java.lang.IllegalArgumentException Ako pib nema tacno 9 cifara.
     */
    public void setPib(String pib) {
        if (pib == null) throw new NullPointerException("PIB ne sme biti null");
        if (!pib.matches("\\d{9}")) throw new IllegalArgumentException("PIB mora imati tacno 9 cifara");
        this.pib = pib;
    }

    /**
     * Poredi dva pravna lica po PIB-u, nazivu firme i mejlu.
     *
     * @param o Drugi objekat sa kojim se poredi.
     * @return
     * <ul>
     * <li><b>true</b> - ako su oba objekta klase PravnoLice sa istim PIB-om,
     * nazivom firme i mejlom ili ako su na istoj adresi.</li>
     * <li><b>false</b> - ako je drugi objekat null, ako je druge klase ili
     * ako se neko od navedenih polja razlikuje.</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PravnoLice pl = (PravnoLice) o;
        return Objects.equals(pib, pl.pib) &&
                Objects.equals(nazivFirme, pl.nazivFirme) &&
                Objects.equals(getMejl(), pl.getMejl());
    }

    /**
     * Racuna hash kod pravnog lica na osnovu PIB-a, naziva firme i mejla.
     *
     * @return Hash kod pravnog lica.
     */
    @Override
    public int hashCode() {
        return Objects.hash(pib, nazivFirme, getMejl());
    }

    /**
     * Vraca tekstualnu reprezentaciju pravnog lica.
     *
     * @return Tekstualna reprezentacija pravnog lica.
     */
    @Override
    public String toString() {
        return "PravnoLice{pib='" + pib + "', nazivFirme='" + nazivFirme +
                "', mejl='" + getMejl() + "'}";
    }
}
