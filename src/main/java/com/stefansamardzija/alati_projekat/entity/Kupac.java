package com.stefansamardzija.alati_projekat.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idKupac;

    @Column(nullable = false)
    private String mejl;

    @ManyToOne
    @JoinColumn(name = "id_mesto")
    private Mesto mesto;

    public Kupac() {
    }

    public Kupac(String mejl, Mesto mesto) {
        this.mejl = mejl;
        this.mesto = mesto;
    }

    public Long getIdKupac() {
        return idKupac;
    }

    public void setIdKupac(Long idKupac) {
        this.idKupac = idKupac;
    }

    public String getMejl() {
        return mejl;
    }

    public void setMejl(String mejl) {
        if (mejl == null) throw new NullPointerException("Mejl ne sme biti null");
        if (!mejl.contains("@")) throw new IllegalArgumentException("Mejl mora sadrzati @");
        this.mejl = mejl;
    }

    public Mesto getMesto() {
        return mesto;
    }

    public void setMesto(Mesto mesto) {
        if (mesto == null) throw new NullPointerException("Mesto ne sme biti null");
        this.mesto = mesto;
    }

    @Transient
    public abstract String getTip();
}
