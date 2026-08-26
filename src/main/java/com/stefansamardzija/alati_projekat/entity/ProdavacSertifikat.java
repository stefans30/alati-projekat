package com.stefansamardzija.alati_projekat.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "prodavac_sertifikat")
public class ProdavacSertifikat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_prodavac")
    @JsonBackReference("prodavac-sertifikati")
    private Prodavac prodavac;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_sertifikat")
    private Sertifikat sertifikat;

    @Column(nullable = false)
    private LocalDate datumIzdavanja;

    public ProdavacSertifikat() {
    }

    public ProdavacSertifikat(Prodavac prodavac, Sertifikat sertifikat, LocalDate datumIzdavanja) {
        this.prodavac = prodavac;
        this.sertifikat = sertifikat;
        this.datumIzdavanja = datumIzdavanja;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Prodavac getProdavac() {
        return prodavac;
    }

    public void setProdavac(Prodavac prodavac) {
        if (prodavac == null) throw new NullPointerException("Prodavac ne sme biti null");
        this.prodavac = prodavac;
    }

    public Sertifikat getSertifikat() {
        return sertifikat;
    }

    public void setSertifikat(Sertifikat sertifikat) {
        if (sertifikat == null) throw new NullPointerException("Sertifikat ne sme biti null");
        this.sertifikat = sertifikat;
    }

    public LocalDate getDatumIzdavanja() {
        return datumIzdavanja;
    }

    public void setDatumIzdavanja(LocalDate datumIzdavanja) {
        if (datumIzdavanja == null) throw new NullPointerException("Datum izdavanja ne sme biti null");
        this.datumIzdavanja = datumIzdavanja;
    }
}
