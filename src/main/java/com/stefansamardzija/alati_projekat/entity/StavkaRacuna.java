package com.stefansamardzija.alati_projekat.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "stavka_racuna")
public class StavkaRacuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStavkaRacuna;

    @Column(nullable = false)
    private int kolicina;
    @Column(nullable = false)
    private BigDecimal cena;

    @Column(nullable = false)
    private BigDecimal iznos;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_telefon")
    private Telefon telefon;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_racun")
    @JsonBackReference("racun-stavke")
    private Racun racun;

    public StavkaRacuna() {
    }

    public StavkaRacuna(BigDecimal cena, int kolicina, Telefon telefon) {
        this.cena = cena;
        this.kolicina = kolicina;
        this.telefon = telefon;
        this.iznos = cena.multiply(BigDecimal.valueOf(kolicina));
    }

    public Long getIdStavkaRacuna() {
        return idStavkaRacuna;
    }

    public void setIdStavkaRacuna(Long idStavkaRacuna) {
        this.idStavkaRacuna = idStavkaRacuna;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        if (kolicina <= 0) throw new IllegalArgumentException("Kolicina mora biti veca od 0");
        this.kolicina = kolicina;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        if (cena == null) throw new NullPointerException("Cena ne sme biti null");
        if (cena.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Cena mora biti veca od 0");
        this.cena = cena;
    }

    public BigDecimal getIznos() {
        return iznos;
    }

    public void setIznos(BigDecimal iznos) {
        if (iznos == null) throw new NullPointerException("Iznos ne sme biti null");
        if (iznos.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Iznos mora biti veci od 0");
        this.iznos = iznos;
    }

    public Telefon getTelefon() {
        return telefon;
    }

    public void setTelefon(Telefon telefon) {
        if (telefon == null) throw new NullPointerException("Telefon ne sme biti null");
        this.telefon = telefon;
    }

    public Racun getRacun() {
        return racun;
    }

    public void setRacun(Racun racun) {
        if (racun == null) throw new NullPointerException("Racun ne sme biti null");
        this.racun = racun;
    }

    public void preracunajIznos() {
        this.iznos = cena.multiply(BigDecimal.valueOf(kolicina));
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(kolicina, telefon, cena, iznos);
    }

    @Override
    public String toString() {
        return "StavkaRacuna{telefon=" + telefon + ", kolicina=" + kolicina +
                ", cena=" + cena + ", iznos=" + iznos + "}";
    }
}
