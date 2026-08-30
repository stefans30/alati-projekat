package com.stefansamardzija.alati_projekat.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;

/**
 * Apstraktna klasa koja predstavlja kupca telefona.
 *
 * Konkretni tipovi kupca su FizickoLice i PravnoLice, koji nasledjuju
 * zajednicke podatke definisane u ovoj klasi.
 *
 * @author Stefan Samardzija
 * @version 1.0
 */
@Entity
@Table(name = "kupac")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tip_kupca")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "tip", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FizickoLice.class, name = "fizicko"),
        @JsonSubTypes.Type(value = PravnoLice.class, name = "pravno")
})
public abstract class Kupac {

    /** Identifikator kupca u bazi podataka. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idKupac;

    /** Mejl adresa kupca kao String. */
    @Column(nullable = false)
    private String mejl;

    /** Mesto iz kog kupac dolazi. */
    @ManyToOne
    @JoinColumn(name = "id_mesto")
    private Mesto mesto;

    /**
     * Prazan konstruktor potreban za JPA.
     */
    public Kupac() {
    }

    /**
     * Kreira novog kupca sa zadatim mejlom i mestom.
     *
     * @param mejl Mejl adresa kupca.
     * @param mesto Mesto iz kog kupac dolazi.
     */
    public Kupac(String mejl, Mesto mesto) {
        this.mejl = mejl;
        this.mesto = mesto;
    }

    /**
     * Vraca identifikator kupca.
     *
     * @return Identifikator kupca.
     */
    public Long getIdKupac() {
        return idKupac;
    }

    /**
     * Postavlja identifikator kupca na unetu vrednost.
     *
     * @param idKupac Novi identifikator kupca.
     */
    public void setIdKupac(Long idKupac) {
        this.idKupac = idKupac;
    }

    /**
     * Vraca mejl adresu kupca.
     *
     * @return Mejl adresa kupca.
     */
    public String getMejl() {
        return mejl;
    }

    /**
     * Postavlja mejl adresu kupca na unetu vrednost.
     *
     * @param mejl Nova mejl adresa kupca.
     * @throws java.lang.NullPointerException Ako je mejl null.
     * @throws java.lang.IllegalArgumentException Ako mejl ne sadrzi karakter '@'.
     */
    public void setMejl(String mejl) {
        if (mejl == null) throw new NullPointerException("Mejl ne sme biti null");
        if (!mejl.contains("@")) throw new IllegalArgumentException("Mejl mora sadrzati @");
        this.mejl = mejl;
    }

    /**
     * Vraca mesto iz kog kupac dolazi.
     *
     * @return Mesto kupca.
     */
    public Mesto getMesto() {
        return mesto;
    }

    /**
     * Postavlja mesto kupca na unetu vrednost.
     *
     * @param mesto Novo mesto kupca.
     * @throws java.lang.NullPointerException Ako je mesto null.
     */
    public void setMesto(Mesto mesto) {
        if (mesto == null) throw new NullPointerException("Mesto ne sme biti null");
        this.mesto = mesto;
    }

    /**
     * Vraca oznaku tipa kupca, u zavisnosti od konkretne podklase.
     *
     * @return Tip kupca ("fizicko" ili "pravno").
     */
    @Transient
    public abstract String getTip();
}
