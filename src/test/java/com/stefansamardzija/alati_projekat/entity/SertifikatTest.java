package com.stefansamardzija.alati_projekat.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class SertifikatTest {

    private Sertifikat sertifikat;

    @BeforeEach
    void setUp() {
        sertifikat = new Sertifikat("Ovlasceni prodavac");
    }

    @AfterEach
    void tearDown() {
        sertifikat = null;
    }

    @Test
    @DisplayName("Konstruktor bez argumenata treba da postavi sva polja na null")
    void testKonstruktorBezArgumenata() {
        Sertifikat prazan = new Sertifikat();
        assertAll("Sva polja moraju biti null nakon kreiranja praznim konstruktorom",
                () -> assertNull(prazan.getIdSertifikat(), "idSertifikat treba da bude null"),
                () -> assertNull(prazan.getZvanje(), "zvanje treba da bude null")
        );
    }

    @Test
    @DisplayName("Konstruktor sa argumentom treba ispravno da postavi zvanje")
    void testKonstruktorSaArgumentima() {
        assertAll("Polje zvanje treba da bude postavljeno na prosledjenu vrednost",
                () -> assertEquals("Ovlasceni prodavac", sertifikat.getZvanje(), "zvanje treba da bude 'Ovlasceni prodavac'"),
                () -> assertNull(sertifikat.getIdSertifikat(), "idSertifikat treba da ostane null jer se ne postavlja kroz konstruktor")
        );
    }

    @Test
    @DisplayName("setZvanje sa validnom vrednoscu treba uspesno da postavi zvanje")
    void testSetZvanjeValidno() {
        sertifikat.setZvanje("Senior prodavac");
        assertEquals("Senior prodavac", sertifikat.getZvanje(), "zvanje treba da bude 'Senior prodavac'");
    }

    @Test
    @DisplayName("setZvanje sa null vrednoscu treba da baci NullPointerException")
    void testSetZvanjeNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> sertifikat.setZvanje(null),
                "Ocekuje se NullPointerException kada je zvanje null");
        assertEquals("Zvanje ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @ParameterizedTest
    @DisplayName("setZvanje sa nevalidnom (prekratkom) vrednoscu treba da baci IllegalArgumentException")
    @CsvSource({"''", "A"})
    void testSetZvanjeNevalidno(String nevalidnoZvanje) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> sertifikat.setZvanje(nevalidnoZvanje),
                "Ocekuje se IllegalArgumentException za zvanje kraci od 2 znaka: '" + nevalidnoZvanje + "'");
        assertEquals("Zvanje mora imati bar 2 znaka", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @Test
    @DisplayName("equals treba da vrati true za isti objekat (refleksivnost)")
    void testEqualsRefleksivnost() {
        assertEquals(sertifikat, sertifikat, "Objekat mora biti jednak samom sebi");
    }

    @Test
    @DisplayName("equals treba da vrati false kada se poredi sa null")
    void testEqualsNull() {
        assertNotEquals(null, sertifikat, "Objekat ne sme biti jednak null vrednosti");
    }

    @Test
    @DisplayName("equals treba da vrati false kada se poredi sa objektom druge klase")
    void testEqualsDrugaKlasa() {
        assertNotEquals(sertifikat, "Ovlasceni prodavac", "Objekat ne sme biti jednak objektu druge klase");
    }

    @ParameterizedTest
    @DisplayName("equals treba ispravno da poredi dva objekta na osnovu zvanja")
    @CsvSource({
            "Ovlasceni prodavac, Ovlasceni prodavac, true",
            "Ovlasceni prodavac, Senior prodavac, false"
    })
    void testEqualsKombinacije(String zvanje1, String zvanje2, boolean ocekivano) {
        Sertifikat prvi = new Sertifikat(zvanje1);
        Sertifikat drugi = new Sertifikat(zvanje2);
        assertEquals(ocekivano, prvi.equals(drugi),
                "equals za zvanje1='" + zvanje1 + "' i zvanje2='" + zvanje2 + "' treba da vrati " + ocekivano);
    }

    @Test
    @DisplayName("hashCode treba da bude isti za objekte sa istim zvanjem")
    void testHashCodeJednakiObjekti() {
        Sertifikat drugi = new Sertifikat("Ovlasceni prodavac");
        assertEquals(sertifikat.hashCode(), drugi.hashCode(), "hashCode mora biti isti za jednake objekte");
    }

    @Test
    @DisplayName("hashCode treba da bude razlicit za objekte sa razlicitim zvanjem")
    void testHashCodeRazlicitiObjekti() {
        Sertifikat drugi = new Sertifikat("Senior prodavac");
        assertNotEquals(sertifikat.hashCode(), drugi.hashCode(), "hashCode treba da bude razlicit za razlicite objekte");
    }

    @Test
    @DisplayName("toString treba da vrati ocekivan format")
    void testToString() {
        assertEquals("Sertifikat{zvanje='Ovlasceni prodavac'}", sertifikat.toString(), "toString format se ne poklapa sa ocekivanim");
    }
}
