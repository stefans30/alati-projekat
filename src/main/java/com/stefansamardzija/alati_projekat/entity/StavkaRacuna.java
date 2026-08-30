package com.stefansamardzija.alati_projekat.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Predstavlja jednu stavku racuna, odnosno jedan telefon prodat u okviru racuna.
 *
 * Sadrzi kolicinu, prodajnu cenu i ukupan iznos stavke, koji se racuna
 * kao proizvod prodajne cene i kolicine.
 *
 * @author Stefan Samardzija
 * @version 1.0
 */
@Entity
@Table(name = "stavka_racuna")
public class StavkaRacuna {

    /** Identifikator stavke racuna u bazi podataka. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStavkaRacuna;

    /** Kolicina prodatih telefona u okviru stavke. */
    @Column(nullable = false)
    private int kolicina;

    /** Prodajna cena telefona u okviru stavke kao BigDecimal. */
    @Column(nullable = false)
    private BigDecimal cena;

    /** Ukupan iznos stavke kao BigDecimal. */
    @Column(nullable = false)
    private BigDecimal iznos;

    /** Telefon na koji se stavka odnosi. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_telefon")
    private Telefon telefon;

    /** Racun kome stavka pripada. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_racun")
    @JsonBackReference("racun-stavke")
    private Racun racun;

    /**
     * Prazan konstruktor potreban za JPA.
     */
    public StavkaRacuna() {
    }

    /**
     * Kreira novu stavku racuna sa zadatom prodajnom cenom, kolicinom
     * i telefonom. Iznos stavke se odmah racuna kao proizvod prodajne
     * cene i kolicine.
     *
     * @param cena Prodajna cena telefona.
     * @param kolicina Kolicina prodatih telefona.
     * @param telefon Telefon na koji se stavka odnosi.
     */
    public StavkaRacuna(BigDecimal cena, int kolicina, Telefon telefon) {
        this.cena = cena;
        this.kolicina = kolicina;
        this.telefon = telefon;
        this.iznos = cena.multiply(BigDecimal.valueOf(kolicina));
    }

    /**
     * Vraca identifikator stavke racuna.
     *
     * @return Identifikator stavke racuna.
     */
    public Long getIdStavkaRacuna() {
        return idStavkaRacuna;
    }

    /**
     * Postavlja identifikator stavke racuna na unetu vrednost.
     *
     * @param idStavkaRacuna Novi identifikator stavke racuna.
     */
    public void setIdStavkaRacuna(Long idStavkaRacuna) {
        this.idStavkaRacuna = idStavkaRacuna;
    }

    /**
     * Vraca kolicinu prodatih telefona u okviru stavke.
     *
     * @return Kolicina prodatih telefona.
     */
    public int getKolicina() {
        return kolicina;
    }

    /**
     * Postavlja kolicinu prodatih telefona u okviru stavke na unetu vrednost.
     *
     * @param kolicina Nova kolicina prodatih telefona.
     * @throws java.lang.IllegalArgumentException Ako kolicina nije veca od 0.
     */
    public void setKolicina(int kolicina) {
        if (kolicina <= 0) throw new IllegalArgumentException("Kolicina mora biti veca od 0");
        this.kolicina = kolicina;
    }

    /**
     * Vraca prodajnu cenu telefona u okviru stavke.
     *
     * @return Prodajna cena telefona.
     */
    public BigDecimal getCena() {
        return cena;
    }

    /**
     * Postavlja prodajnu cenu telefona u okviru stavke na unetu vrednost.
     *
     * @param cena Nova prodajna cena telefona.
     * @throws java.lang.NullPointerException Ako je cena null.
     * @throws java.lang.IllegalArgumentException Ako cena nije veca od 0.
     */
    public void setCena(BigDecimal cena) {
        if (cena == null) throw new NullPointerException("Cena ne sme biti null");
        if (cena.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Cena mora biti veca od 0");
        this.cena = cena;
    }

    /**
     * Vraca ukupan iznos stavke.
     *
     * @return Iznos stavke.
     */
    public BigDecimal getIznos() {
        return iznos;
    }

    /**
     * Postavlja ukupan iznos stavke na unetu vrednost.
     *
     * @param iznos Novi iznos stavke.
     * @throws java.lang.NullPointerException Ako je iznos null.
     * @throws java.lang.IllegalArgumentException Ako iznos nije veci od 0.
     */
    public void setIznos(BigDecimal iznos) {
        if (iznos == null) throw new NullPointerException("Iznos ne sme biti null");
        if (iznos.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Iznos mora biti veci od 0");
        this.iznos = iznos;
    }

    /**
     * Vraca telefon na koji se stavka odnosi.
     *
     * @return Telefon.
     */
    public Telefon getTelefon() {
        return telefon;
    }

    /**
     * Postavlja telefon na koji se stavka odnosi na unetu vrednost.
     *
     * @param telefon Novi telefon stavke.
     * @throws java.lang.NullPointerException Ako je telefon null.
     */
    public void setTelefon(Telefon telefon) {
        if (telefon == null) throw new NullPointerException("Telefon ne sme biti null");
        this.telefon = telefon;
    }

    /**
     * Vraca racun kome stavka pripada.
     *
     * @return Racun.
     */
    public Racun getRacun() {
        return racun;
    }

    /**
     * Postavlja racun kome stavka pripada na unetu vrednost.
     *
     * @param racun Novi racun stavke.
     * @throws java.lang.NullPointerException Ako je racun null.
     */
    public void setRacun(Racun racun) {
        if (racun == null) throw new NullPointerException("Racun ne sme biti null");
        this.racun = racun;
    }

    /**
     * Preracunava iznos stavke kao proizvod prodajne cene i kolicine.
     *
     * Rezultat mnozenja se dodeljuje polju iznos, zamenjujuci
     * njegovu prethodnu vrednost.
     */
    public void preracunajIznos() {
        this.iznos = cena.multiply(BigDecimal.valueOf(kolicina));
    }

    /**
     * Poredi dve stavke racuna po kolicini, telefonu, ceni i iznosu.
     *
     * @param o Drugi objekat sa kojim se poredi.
     * @return
     * <ul>
     * <li><b>true</b> - ako su oba objekta klase StavkaRacuna sa istom
     * kolicinom, telefonom, cenom i iznosom ili ako su na istoj adresi.</li>
     * <li><b>false</b> - ako je drugi objekat null, ako je druge klase ili
     * ako se neko od navedenih polja razlikuje.</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StavkaRacuna s = (StavkaRacuna) o;
        return kolicina == s.kolicina &&
                Objects.equals(telefon, s.telefon) &&
                Objects.equals(cena, s.cena) &&
                Objects.equals(iznos, s.iznos);
    }

    /**
     * Racuna hash kod stavke racuna na osnovu kolicine, telefona,
     * cene i iznosa.
     *
     * @return Hash kod stavke racuna.
     */
    @Override
    public int hashCode() {
        return Objects.hash(kolicina, telefon, cena, iznos);
    }

    /**
     * Vraca tekstualnu reprezentaciju stavke racuna.
     *
     * @return Tekstualna reprezentacija stavke racuna.
     */
    @Override
    public String toString() {
        return "StavkaRacuna{telefon=" + telefon + ", kolicina=" + kolicina +
                ", cena=" + cena + ", iznos=" + iznos + "}";
    }
}
