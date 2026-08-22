package com.stefansamardzija.alati_projekat.model;

import jakarta.persistence.*;

@Entity
public class Sertifikat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSertifikat;

    private String zvanje;

    public Sertifikat() {
    }

    public Sertifikat(String zvanje) {
        this.zvanje = zvanje;
    }

    public Long getIdSertifikat() {
        return idSertifikat;
    }

    public void setIdSertifikat(Long idSertifikat) {
        this.idSertifikat = idSertifikat;
    }

    public String getZvanje() {
        return zvanje;
    }

    public void setZvanje(String zvanje) {
        this.zvanje = zvanje;
    }
}
