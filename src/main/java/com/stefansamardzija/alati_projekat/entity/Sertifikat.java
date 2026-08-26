package com.stefansamardzija.alati_projekat.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "sertifikat")
public class Sertifikat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSertifikat;

    @Column(nullable = false)
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
        if (zvanje == null) throw new NullPointerException("Zvanje ne sme biti null");
        if (zvanje.length() < 2) throw new IllegalArgumentException("Zvanje mora imati bar 2 znaka");
        this.zvanje = zvanje;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sertifikat s = (Sertifikat) o;
        return Objects.equals(zvanje, s.zvanje);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zvanje);
    }

    @Override
    public String toString() {
        return "Sertifikat{zvanje='" + zvanje + "'}";
    }
}
