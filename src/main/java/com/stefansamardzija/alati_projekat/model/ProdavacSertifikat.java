package com.stefansamardzija.alati_projekat.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class ProdavacSertifikat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate datumIzdavanja;

    @ManyToOne
    @JoinColumn(name = "idProdavac")
    private Prodavac prodavac;

    @ManyToOne
    @JoinColumn(name = "idSertifikat")
    private Sertifikat sertifikat;

    public ProdavacSertifikat() {
    }

    public ProdavacSertifikat(LocalDate datumIzdavanja, Prodavac prodavac, Sertifikat sertifikat) {
        this.datumIzdavanja = datumIzdavanja;
        this.prodavac = prodavac;
        this.sertifikat = sertifikat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumIzdavanja() {
        return datumIzdavanja;
    }

    public void setDatumIzdavanja(LocalDate datumIzdavanja) {
        this.datumIzdavanja = datumIzdavanja;
    }

    public Prodavac getProdavac() {
        return prodavac;
    }

    public void setProdavac(Prodavac prodavac) {
        this.prodavac = prodavac;
    }

    public Sertifikat getSertifikat() {
        return sertifikat;
    }

    public void setSertifikat(Sertifikat sertifikat) {
        this.sertifikat = sertifikat;
    }
}
