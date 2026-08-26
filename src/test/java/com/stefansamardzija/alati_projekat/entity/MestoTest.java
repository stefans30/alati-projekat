package com.stefansamardzija.alati_projekat.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class MestoTest {

    private Mesto mesto;

    @BeforeEach
    void setUp() {
        mesto = new Mesto("Novi Sad");
    }

    @AfterEach
    void tearDown() {
        mesto = null;
    }

    @Test
    @DisplayName("Konstruktor bez argumenata treba da postavi sva polja na null")
    void testKonstruktorBezArgumenata() {
        Mesto prazno = new Mesto();
        assertAll("Sva polja moraju biti null nakon kreiranja praznim konstruktorom",
                () -> assertNull(prazno.getIdMesto(), "idMesto treba da bude null"),
                () -> assertNull(prazno.getNaziv(), "naziv treba da bude null")
        );
    }

    @Test
    @DisplayName("Konstruktor sa argumentom treba ispravno da postavi naziv")
    void testKonstruktorSaArgumentima() {
        Mesto m = new Mesto("Beograd");
        assertAll("Polje naziv treba da bude postavljeno na prosledjenu vrednost",
                () -> assertEquals("Beograd", m.getNaziv(), "naziv treba da bude 'Beograd'"),
                () -> assertNull(m.getIdMesto(), "idMesto treba da ostane null jer se ne postavlja kroz konstruktor")
        );
    }

    @Test
    @DisplayName("setIdMesto treba da postavi vrednost idMesto polja")
    void testSetIdMesto() {
        mesto.setIdMesto(5L);
        assertEquals(5L, mesto.getIdMesto(), "idMesto treba da bude 5");
    }

    @Test
    @DisplayName("setNaziv sa validnom vrednoscu treba uspesno da postavi naziv")
    void testSetNazivValidno() {
        mesto.setNaziv("Subotica");
        assertEquals("Subotica", mesto.getNaziv(), "naziv treba da bude 'Subotica'");
    }

    @Test
    @DisplayName("setNaziv sa null vrednoscu treba da baci NullPointerException")
    void testSetNazivNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> mesto.setNaziv(null),
                "Ocekuje se NullPointerException kada je naziv null");
        assertEquals("Naziv ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @ParameterizedTest
    @DisplayName("setNaziv sa nevalidnom (prekratkom) vrednoscu treba da baci IllegalArgumentException")
    @CsvSource({"''", "A"})
    void testSetNazivNevalidno(String nevalidanNaziv) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> mesto.setNaziv(nevalidanNaziv),
                "Ocekuje se IllegalArgumentException za naziv kraci od 2 znaka: '" + nevalidanNaziv + "'");
        assertEquals("Naziv mora imati bar 2 znaka", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @Test
    @DisplayName("equals treba da vrati true za isti objekat (refleksivnost)")
    void testEqualsRefleksivnost() {
        assertEquals(mesto, mesto, "Objekat mora biti jednak samom sebi");
    }

    @Test
    @DisplayName("equals treba da vrati false kada se poredi sa null")
    void testEqualsNull() {
        assertNotEquals(null, mesto, "Objekat ne sme biti jednak null vrednosti");
    }

    @Test
    @DisplayName("equals treba da vrati false kada se poredi sa objektom druge klase")
    void testEqualsDrugaKlasa() {
        assertNotEquals(mesto, "Novi Sad", "Objekat ne sme biti jednak objektu druge klase");
    }

    @ParameterizedTest
    @DisplayName("equals treba ispravno da poredi dva objekta na osnovu naziva")
    @CsvSource({
            "Novi Sad, Novi Sad, true",
            "Novi Sad, Beograd, false"
    })
    void testEqualsKombinacije(String naziv1, String naziv2, boolean ocekivano) {
        Mesto prvi = new Mesto(naziv1);
        Mesto drugi = new Mesto(naziv2);
        assertEquals(ocekivano, prvi.equals(drugi),
                "equals za naziv1='" + naziv1 + "' i naziv2='" + naziv2 + "' treba da vrati " + ocekivano);
    }

    @Test
    @DisplayName("hashCode treba da bude isti za objekte sa istim nazivom")
    void testHashCodeJednakiObjekti() {
        Mesto drugi = new Mesto("Novi Sad");
        assertEquals(mesto.hashCode(), drugi.hashCode(), "hashCode mora biti isti za jednake objekte");
    }

    @Test
    @DisplayName("hashCode treba da bude razlicit za objekte sa razlicitim nazivom")
    void testHashCodeRazlicitiObjekti() {
        Mesto drugi = new Mesto("Beograd");
        assertNotEquals(mesto.hashCode(), drugi.hashCode(), "hashCode treba da bude razlicit za razlicite objekte");
    }

    @Test
    @DisplayName("toString treba da vrati ocekivan format")
    void testToString() {
        assertEquals("Mesto{naziv='Novi Sad'}", mesto.toString(), "toString format se ne poklapa sa ocekivanim");
    }
}
