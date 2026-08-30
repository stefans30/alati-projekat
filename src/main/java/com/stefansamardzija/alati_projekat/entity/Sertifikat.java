package com.stefansamardzija.alati_projekat.entity;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * Predstavlja sertifikat (zvanje) koje prodavac moze posedovati.
 *
 * Koristi se u vezi sa klasom ProdavacSertifikat radi evidencije dodele
 * sertifikata prodavcima.
 *
 * @author Stefan Samardzija
 * @version 1.0
 */
@Entity
@Table(name = "sertifikat")
public class Sertifikat {

    /** Identifikator sertifikata u bazi podataka. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSertifikat;

    /** Zvanje sertifikata kao String. */
    @Column(nullable = false)
    private String zvanje;

    /**
     * Prazan konstruktor potreban za JPA.
     */
    public Sertifikat() {
    }

    /**
     * Kreira novi sertifikat sa zadatim zvanjem.
     *
     * @param zvanje Zvanje sertifikata.
     */
    public Sertifikat(String zvanje) {
        this.zvanje = zvanje;
    }

    /**
     * Vraca identifikator sertifikata.
     *
     * @return Identifikator sertifikata.
     */
    public Long getIdSertifikat() {
        return idSertifikat;
    }

    /**
     * Postavlja identifikator sertifikata na unetu vrednost.
     *
     * @param idSertifikat Novi identifikator sertifikata.
     */
    public void setIdSertifikat(Long idSertifikat) {
        this.idSertifikat = idSertifikat;
    }

    /**
     * Vraca zvanje sertifikata.
     *
     * @return Zvanje sertifikata.
     */
    public String getZvanje() {
        return zvanje;
    }

    /**
     * Postavlja zvanje sertifikata na unetu vrednost.
     *
     * @param zvanje Novo zvanje sertifikata.
     * @throws java.lang.NullPointerException Ako je zvanje null.
     * @throws java.lang.IllegalArgumentException Ako zvanje ima manje od 2 znaka.
     */
    public void setZvanje(String zvanje) {
        if (zvanje == null) throw new NullPointerException("Zvanje ne sme biti null");
        if (zvanje.length() < 2) throw new IllegalArgumentException("Zvanje mora imati bar 2 znaka");
        this.zvanje = zvanje;
    }

    /**
     * Poredi dva sertifikata po zvanju.
     *
     * @param o Drugi objekat sa kojim se poredi.
     * @return
     * <ul>
     * <li><b>true</b> - ako su oba objekta klase Sertifikat sa istim zvanjem
     * ili ako su na istoj adresi.</li>
     * <li><b>false</b> - ako je drugi objekat null, ako je druge klase ili
     * ako zvanje nije isto.</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sertifikat s = (Sertifikat) o;
        return Objects.equals(zvanje, s.zvanje);
    }

    /**
     * Racuna hash kod sertifikata na osnovu zvanja.
     *
     * @return Hash kod sertifikata.
     */
    @Override
    public int hashCode() {
        return Objects.hash(zvanje);
    }

    /**
     * Vraca tekstualnu reprezentaciju sertifikata.
     *
     * @return Tekstualna reprezentacija sertifikata.
     */
    @Override
    public String toString() {
        return "Sertifikat{zvanje='" + zvanje + "'}";
    }
}
