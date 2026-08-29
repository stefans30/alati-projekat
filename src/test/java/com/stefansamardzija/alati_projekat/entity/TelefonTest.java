package com.stefansamardzija.alati_projekat.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TelefonTest {

    private Telefon telefon;

    @BeforeEach
    void setUp() {
        telefon = new Telefon("iPhone 15", new BigDecimal("999.99"), "128GB, crna boja");
    }

    @AfterEach
    void tearDown() {
        telefon = null;
    }

    @Test
    @DisplayName("Konstruktor bez argumenata treba da postavi sva polja na null")
    void testKonstruktorBezArgumenata() {
        Telefon prazan = new Telefon();
        assertAll("Sva polja moraju biti null nakon kreiranja praznim konstruktorom",
                () -> assertNull(prazan.getIdTelefon(), "idTelefon treba da bude null"),
                () -> assertNull(prazan.getNaziv(), "naziv treba da bude null"),
                () -> assertNull(prazan.getCena(), "cena treba da bude null"),
                () -> assertNull(prazan.getSpecifikacije(), "specifikacije treba da bude null")
        );
    }

    @Test
    @DisplayName("Konstruktor sa argumentima treba ispravno da postavi sva polja")
    void testKonstruktorSaArgumentima() {
        assertAll("Sva polja moraju biti postavljena na prosledjene vrednosti",
                () -> assertEquals("iPhone 15", telefon.getNaziv(), "naziv treba da bude 'iPhone 15'"),
                () -> assertEquals(new BigDecimal("999.99"), telefon.getCena(), "cena treba da bude 999.99"),
                () -> assertEquals("128GB, crna boja", telefon.getSpecifikacije(), "specifikacije treba da bude '128GB, crna boja'"),
                () -> assertNull(telefon.getIdTelefon(), "idTelefon treba da ostane null jer se ne postavlja kroz konstruktor")
        );
    }

    @Test
    @DisplayName("setNaziv sa validnom vrednoscu treba uspesno da postavi naziv")
    void testSetNazivValidno() {
        telefon.setNaziv("Samsung Galaxy S24");
        assertEquals("Samsung Galaxy S24", telefon.getNaziv(), "naziv treba da bude 'Samsung Galaxy S24'");
    }

    @Test
    @DisplayName("setNaziv sa null vrednoscu treba da baci NullPointerException")
    void testSetNazivNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> telefon.setNaziv(null),
                "Ocekuje se NullPointerException kada je naziv null");
        assertEquals("Naziv ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @ParameterizedTest
    @DisplayName("setNaziv sa nevalidnom (prekratkom) vrednoscu treba da baci IllegalArgumentException")
    @CsvSource({"''", "A"})
    void testSetNazivNevalidno(String nevalidanNaziv) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> telefon.setNaziv(nevalidanNaziv),
                "Ocekuje se IllegalArgumentException za naziv kraci od 2 znaka: '" + nevalidanNaziv + "'");
        assertEquals("Naziv mora imati bar 2 znaka", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @Test
    @DisplayName("setCena sa validnom vrednoscu treba uspesno da postavi cenu")
    void testSetCenaValidno() {
        telefon.setCena(new BigDecimal("1200.00"));
        assertEquals(new BigDecimal("1200.00"), telefon.getCena(), "cena treba da bude 1200.00");
    }

    @Test
    @DisplayName("setCena sa null vrednoscu treba da baci NullPointerException")
    void testSetCenaNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> telefon.setCena(null),
                "Ocekuje se NullPointerException kada je cena null");
        assertEquals("Cena ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @ParameterizedTest
    @DisplayName("setCena sa nevalidnom vrednoscu (<=0) treba da baci IllegalArgumentException")
    @CsvSource({"0", "-1", "-100.50"})
    void testSetCenaNevalidno(String nevalidnaCena) {
        BigDecimal cena = new BigDecimal(nevalidnaCena);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> telefon.setCena(cena),
                "Ocekuje se IllegalArgumentException za cenu manju ili jednaku 0: '" + nevalidnaCena + "'");
        assertEquals("Cena mora biti veca od 0", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @Test
    @DisplayName("setSpecifikacije treba uspesno da postavi vrednost bez validacije")
    void testSetSpecifikacije() {
        telefon.setSpecifikacije("256GB, plava boja");
        assertEquals("256GB, plava boja", telefon.getSpecifikacije(), "specifikacije treba da bude '256GB, plava boja'");
    }

    @Test
    @DisplayName("setSpecifikacije sa null vrednoscu ne baca izuzetak (polje nije obavezno)")
    void testSetSpecifikacijeNull() {
        assertDoesNotThrow(() -> telefon.setSpecifikacije(null), "Specifikacije mogu biti null jer nemaju validaciju");
        assertNull(telefon.getSpecifikacije(), "specifikacije treba da bude null");
    }

    @Test
    @DisplayName("equals treba da vrati true za isti objekat (refleksivnost)")
    void testEqualsRefleksivnost() {
        assertEquals(telefon, telefon, "Objekat mora biti jednak samom sebi");
    }

    @Test
    @DisplayName("equals treba da vrati false kada se poredi sa null")
    void testEqualsNull() {
        assertNotEquals(null, telefon, "Objekat ne sme biti jednak null vrednosti");
    }

    @Test
    @DisplayName("equals treba da vrati false kada se poredi sa objektom druge klase")
    void testEqualsDrugaKlasa() {
        assertNotEquals(telefon, "iPhone 15", "Objekat ne sme biti jednak objektu druge klase");
    }

    @ParameterizedTest
    @DisplayName("equals treba ispravno da poredi dva objekta na osnovu naziva i cene (specifikacije se ne uzimaju u obzir)")
    @CsvSource({
            "iPhone 15, 999.99, 'neke specifikacije', true",
            "'Samsung Galaxy', 999.99, '128GB, crna boja', false",
            "iPhone 15, 500.00, '128GB, crna boja', false"
    })
    void testEqualsKombinacije(String naziv, String cena, String specifikacije, boolean ocekivano) {
        Telefon drugi = new Telefon(naziv, new BigDecimal(cena), specifikacije);
        assertEquals(ocekivano, telefon.equals(drugi),
                "equals rezultat se ne poklapa sa ocekivanim za kombinaciju: " + naziv + ", " + cena);
    }

    @Test
    @DisplayName("hashCode treba da bude isti za objekte sa istim nazivom i cenom (razlicite specifikacije)")
    void testHashCodeJednakiObjekti() {
        Telefon drugi = new Telefon("iPhone 15", new BigDecimal("999.99"), "druge specifikacije");
        assertEquals(telefon.hashCode(), drugi.hashCode(), "hashCode mora biti isti za jednake objekte");
    }

    @Test
    @DisplayName("hashCode treba da bude razlicit za objekte sa razlicitom cenom")
    void testHashCodeRazlicitiObjekti() {
        Telefon drugi = new Telefon("iPhone 15", new BigDecimal("500.00"), "128GB, crna boja");
        assertNotEquals(telefon.hashCode(), drugi.hashCode(), "hashCode treba da bude razlicit za razlicite objekte");
    }

    @Test
    @DisplayName("toString treba da vrati ocekivan format")
    void testToString() {
        assertEquals("Telefon{naziv='iPhone 15', cena=999.99}", telefon.toString(), "toString format se ne poklapa sa ocekivanim");
    }
}
