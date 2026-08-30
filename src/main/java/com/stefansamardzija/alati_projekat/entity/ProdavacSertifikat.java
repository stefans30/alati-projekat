package com.stefansamardzija.alati_projekat.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Predstavlja vezu izmedju prodavca i sertifikata koji je stekao,
 * zajedno sa datumom izdavanja.
 *
 * Sluzi kao spojna tabela u vezi vise-na-vise izmedju Prodavac i Sertifikat.
 *
 * @author Stefan Samardzija
 * @version 1.0
 */
@Entity
@Table(name = "prodavac_sertifikat")
public class ProdavacSertifikat {

    /** Identifikator zapisa u bazi podataka. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Prodavac koji je stekao sertifikat. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_prodavac")
    @JsonBackReference("prodavac-sertifikati")
    private Prodavac prodavac;

    /** Sertifikat koji je prodavac stekao. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_sertifikat")
    private Sertifikat sertifikat;

    /** Datum izdavanja sertifikata. */
    @Column(nullable = false)
    private LocalDate datumIzdavanja;

    /**
     * Prazan konstruktor potreban za JPA.
     */
    public ProdavacSertifikat() {
    }

    /**
     * Kreira novu vezu izmedju prodavca i sertifikata sa zadatim datumom.
     *
     * @param prodavac Prodavac koji je stekao sertifikat.
     * @param sertifikat Sertifikat koji je prodavac stekao.
     * @param datumIzdavanja Datum izdavanja sertifikata.
     */
    public ProdavacSertifikat(Prodavac prodavac, Sertifikat sertifikat, LocalDate datumIzdavanja) {
        this.prodavac = prodavac;
        this.sertifikat = sertifikat;
        this.datumIzdavanja = datumIzdavanja;
    }

    /**
     * Vraca identifikator zapisa.
     *
     * @return Identifikator zapisa.
     */
    public Long getId() {
        return id;
    }

    /**
     * Postavlja identifikator zapisa na unetu vrednost.
     *
     * @param id Novi identifikator zapisa.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Vraca prodavca koji je stekao sertifikat.
     *
     * @return Prodavac.
     */
    public Prodavac getProdavac() {
        return prodavac;
    }

    /**
     * Postavlja prodavca na unetu vrednost.
     *
     * @param prodavac Novi prodavac.
     * @throws java.lang.NullPointerException Ako je prodavac null.
     */
    public void setProdavac(Prodavac prodavac) {
        if (prodavac == null) throw new NullPointerException("Prodavac ne sme biti null");
        this.prodavac = prodavac;
    }

    /**
     * Vraca sertifikat koji je prodavac stekao.
     *
     * @return Sertifikat.
     */
    public Sertifikat getSertifikat() {
        return sertifikat;
    }

    /**
     * Postavlja sertifikat na unetu vrednost.
     *
     * @param sertifikat Novi sertifikat.
     * @throws java.lang.NullPointerException Ako je sertifikat null.
     */
    public void setSertifikat(Sertifikat sertifikat) {
        if (sertifikat == null) throw new NullPointerException("Sertifikat ne sme biti null");
        this.sertifikat = sertifikat;
    }

    /**
     * Vraca datum izdavanja sertifikata.
     *
     * @return Datum izdavanja sertifikata.
     */
    public LocalDate getDatumIzdavanja() {
        return datumIzdavanja;
    }

    /**
     * Postavlja datum izdavanja sertifikata na unetu vrednost.
     *
     * @param datumIzdavanja Novi datum izdavanja sertifikata.
     * @throws java.lang.NullPointerException Ako je datum null.
     */
    public void setDatumIzdavanja(LocalDate datumIzdavanja) {
        if (datumIzdavanja == null) throw new NullPointerException("Datum izdavanja ne sme biti null");
        this.datumIzdavanja = datumIzdavanja;
    }
}
