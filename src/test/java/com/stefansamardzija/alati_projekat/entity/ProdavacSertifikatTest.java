package com.stefansamardzija.alati_projekat.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Klasa ProdavacSertifikat ne redefinise equals/hashCode/toString (koristi Object podrazumevanu
 * implementaciju), pa se ti testovi ne rade - u skladu sa zahtevom da se testira "gde ima smisla".
 */
class ProdavacSertifikatTest {

    private Prodavac prodavac;
    private Sertifikat sertifikat;
    private LocalDate datumIzdavanja;
    private ProdavacSertifikat prodavacSertifikat;

    @BeforeEach
    void setUp() {
        prodavac = new Prodavac("Pera", "Peric", "pera@example.com", "pperic", "sifra123");
        sertifikat = new Sertifikat("Ovlasceni prodavac");
        datumIzdavanja = LocalDate.of(2024, 1, 15);
        prodavacSertifikat = new ProdavacSertifikat(prodavac, sertifikat, datumIzdavanja);
    }

    @AfterEach
    void tearDown() {
        prodavac = null;
        sertifikat = null;
        datumIzdavanja = null;
        prodavacSertifikat = null;
    }

    @Test
    @DisplayName("Konstruktor bez argumenata treba da postavi sva polja na null")
    void testKonstruktorBezArgumenata() {
        ProdavacSertifikat prazan = new ProdavacSertifikat();
        assertAll("Sva polja moraju biti null nakon kreiranja praznim konstruktorom",
                () -> assertNull(prazan.getId(), "id treba da bude null"),
                () -> assertNull(prazan.getProdavac(), "prodavac treba da bude null"),
                () -> assertNull(prazan.getSertifikat(), "sertifikat treba da bude null"),
                () -> assertNull(prazan.getDatumIzdavanja(), "datumIzdavanja treba da bude null")
        );
    }

    @Test
    @DisplayName("Konstruktor sa argumentima treba ispravno da postavi sva polja")
    void testKonstruktorSaArgumentima() {
        assertAll("Sva polja moraju biti postavljena na prosledjene vrednosti",
                () -> assertEquals(prodavac, prodavacSertifikat.getProdavac(), "prodavac treba da bude prosledjeni objekat"),
                () -> assertEquals(sertifikat, prodavacSertifikat.getSertifikat(), "sertifikat treba da bude prosledjeni objekat"),
                () -> assertEquals(datumIzdavanja, prodavacSertifikat.getDatumIzdavanja(), "datumIzdavanja treba da bude prosledjena vrednost"),
                () -> assertNull(prodavacSertifikat.getId(), "id treba da ostane null jer se ne postavlja kroz konstruktor")
        );
    }

    @Test
    @DisplayName("setProdavac sa validnom vrednoscu treba uspesno da postavi prodavca")
    void testSetProdavacValidno() {
        Prodavac drugiProdavac = new Prodavac("Mika", "Jovic", "mika@example.com", "mjovic", "sifra456");
        prodavacSertifikat.setProdavac(drugiProdavac);
        assertEquals(drugiProdavac, prodavacSertifikat.getProdavac(), "prodavac treba da bude drugi prodavac");
    }

    @Test
    @DisplayName("setProdavac sa null vrednoscu treba da baci NullPointerException")
    void testSetProdavacNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> prodavacSertifikat.setProdavac(null),
                "Ocekuje se NullPointerException kada je prodavac null");
        assertEquals("Prodavac ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }


    @Test
    @DisplayName("setSertifikat sa validnom vrednoscu treba uspesno da postavi sertifikat")
    void testSetSertifikatValidno() {
        Sertifikat drugiSertifikat = new Sertifikat("Senior prodavac");
        prodavacSertifikat.setSertifikat(drugiSertifikat);
        assertEquals(drugiSertifikat, prodavacSertifikat.getSertifikat(), "sertifikat treba da bude drugi sertifikat");
    }

    @Test
    @DisplayName("setSertifikat sa null vrednoscu treba da baci NullPointerException")
    void testSetSertifikatNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> prodavacSertifikat.setSertifikat(null),
                "Ocekuje se NullPointerException kada je sertifikat null");
        assertEquals("Sertifikat ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }


    @Test
    @DisplayName("setDatumIzdavanja sa validnom vrednoscu treba uspesno da postavi datum")
    void testSetDatumIzdavanjaValidno() {
        LocalDate noviDatum = LocalDate.of(2025, 6, 1);
        prodavacSertifikat.setDatumIzdavanja(noviDatum);
        assertEquals(noviDatum, prodavacSertifikat.getDatumIzdavanja(), "datumIzdavanja treba da bude novi datum");
    }

    @Test
    @DisplayName("setDatumIzdavanja sa null vrednoscu treba da baci NullPointerException")
    void testSetDatumIzdavanjaNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> prodavacSertifikat.setDatumIzdavanja(null),
                "Ocekuje se NullPointerException kada je datum izdavanja null");
        assertEquals("Datum izdavanja ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }
}
