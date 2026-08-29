package com.stefansamardzija.alati_projekat.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PravnoLiceTest {

    private Mesto mesto;
    private PravnoLice pravnoLice;

    @BeforeEach
    void setUp() {
        mesto = new Mesto("Novi Sad");
        pravnoLice = new PravnoLice("firma@example.com", mesto, "Firma DOO", "123456789");
    }

    @AfterEach
    void tearDown() {
        mesto = null;
        pravnoLice = null;
    }

    @Test
    @DisplayName("Konstruktor bez argumenata treba da postavi sva polja na null")
    void testKonstruktorBezArgumenata() {
        PravnoLice prazno = new PravnoLice();
        assertAll("Sva polja moraju biti null nakon kreiranja praznim konstruktorom",
                () -> assertNull(prazno.getIdKupac(), "idKupac treba da bude null"),
                () -> assertNull(prazno.getMejl(), "mejl treba da bude null"),
                () -> assertNull(prazno.getMesto(), "mesto treba da bude null"),
                () -> assertNull(prazno.getNazivFirme(), "nazivFirme treba da bude null"),
                () -> assertNull(prazno.getPib(), "pib treba da bude null")
        );
    }

    @Test
    @DisplayName("Konstruktor sa argumentima treba ispravno da postavi sva polja")
    void testKonstruktorSaArgumentima() {
        assertAll("Sva polja moraju biti postavljena na prosledjene vrednosti",
                () -> assertEquals("firma@example.com", pravnoLice.getMejl(), "mejl treba da bude 'firma@example.com'"),
                () -> assertEquals(mesto, pravnoLice.getMesto(), "mesto treba da bude prosledjeni objekat"),
                () -> assertEquals("Firma DOO", pravnoLice.getNazivFirme(), "nazivFirme treba da bude 'Firma DOO'"),
                () -> assertEquals("123456789", pravnoLice.getPib(), "pib treba da bude '123456789'"),
                () -> assertNull(pravnoLice.getIdKupac(), "idKupac treba da ostane null jer se ne postavlja kroz konstruktor")
        );
    }

    @Test
    @DisplayName("getTip treba da vrati 'pravno'")
    void testGetTip() {
        assertEquals("pravno", pravnoLice.getTip(), "getTip treba da vrati 'pravno'");
    }


    @Test
    @DisplayName("setMejl sa validnom vrednoscu treba uspesno da postavi mejl")
    void testSetMejlValidno() {
        pravnoLice.setMejl("novi@mejl.com");
        assertEquals("novi@mejl.com", pravnoLice.getMejl(), "mejl treba da bude 'novi@mejl.com'");
    }

    @Test
    @DisplayName("setMejl sa null vrednoscu treba da baci NullPointerException")
    void testSetMejlNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> pravnoLice.setMejl(null),
                "Ocekuje se NullPointerException kada je mejl null");
        assertEquals("Mejl ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @ParameterizedTest
    @DisplayName("setMejl sa nevalidnom vrednoscu (bez @) treba da baci IllegalArgumentException")
    @CsvSource({"firmaexample.com", "''", "samotekst"})
    void testSetMejlNevalidno(String nevalidanMejl) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> pravnoLice.setMejl(nevalidanMejl),
                "Ocekuje se IllegalArgumentException za mejl bez '@': '" + nevalidanMejl + "'");
        assertEquals("Mejl mora sadrzati @", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }


    @Test
    @DisplayName("setMesto sa validnom vrednoscu treba uspesno da postavi mesto")
    void testSetMestoValidno() {
        Mesto novoMesto = new Mesto("Beograd");
        pravnoLice.setMesto(novoMesto);
        assertEquals(novoMesto, pravnoLice.getMesto(), "mesto treba da bude 'Beograd'");
    }

    @Test
    @DisplayName("setMesto sa null vrednoscu treba da baci NullPointerException")
    void testSetMestoNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> pravnoLice.setMesto(null),
                "Ocekuje se NullPointerException kada je mesto null");
        assertEquals("Mesto ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @Test
    @DisplayName("setNazivFirme sa validnom vrednoscu treba uspesno da postavi naziv firme")
    void testSetNazivFirmeValidno() {
        pravnoLice.setNazivFirme("Druga Firma DOO");
        assertEquals("Druga Firma DOO", pravnoLice.getNazivFirme(), "nazivFirme treba da bude 'Druga Firma DOO'");
    }

    @Test
    @DisplayName("setNazivFirme sa null vrednoscu treba da baci NullPointerException")
    void testSetNazivFirmeNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> pravnoLice.setNazivFirme(null),
                "Ocekuje se NullPointerException kada je nazivFirme null");
        assertEquals("Naziv firme ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @ParameterizedTest
    @DisplayName("setNazivFirme sa nevalidnom (prekratkom) vrednoscu treba da baci IllegalArgumentException")
    @CsvSource({"''", "A"})
    void testSetNazivFirmeNevalidno(String nevalidanNaziv) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> pravnoLice.setNazivFirme(nevalidanNaziv),
                "Ocekuje se IllegalArgumentException za naziv firme kraci od 2 znaka: '" + nevalidanNaziv + "'");
        assertEquals("Naziv firme mora imati bar 2 znaka", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @Test
    @DisplayName("setPib sa validnom vrednoscu treba uspesno da postavi pib")
    void testSetPibValidno() {
        pravnoLice.setPib("987654321");
        assertEquals("987654321", pravnoLice.getPib(), "pib treba da bude '987654321'");
    }

    @Test
    @DisplayName("setPib sa null vrednoscu treba da baci NullPointerException")
    void testSetPibNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> pravnoLice.setPib(null),
                "Ocekuje se NullPointerException kada je pib null");
        assertEquals("PIB ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @ParameterizedTest
    @DisplayName("setPib sa nevalidnom vrednoscu treba da baci IllegalArgumentException")
    @CsvSource({"12345", "12345678A", "1234567890", "''"})
    void testSetPibNevalidno(String nevalidanPib) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> pravnoLice.setPib(nevalidanPib),
                "Ocekuje se IllegalArgumentException za pib koji ne sadrzi tacno 9 cifara: '" + nevalidanPib + "'");
        assertEquals("PIB mora imati tacno 9 cifara", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @Test
    @DisplayName("equals treba da vrati true za isti objekat (refleksivnost)")
    void testEqualsRefleksivnost() {
        assertEquals(pravnoLice, pravnoLice, "Objekat mora biti jednak samom sebi");
    }

    @Test
    @DisplayName("equals treba da vrati false kada se poredi sa null")
    void testEqualsNull() {
        assertNotEquals(null, pravnoLice, "Objekat ne sme biti jednak null vrednosti");
    }

    @Test
    @DisplayName("equals treba da vrati false kada se poredi sa objektom druge klase")
    void testEqualsDrugaKlasa() {
        assertNotEquals(pravnoLice, "Firma DOO", "Objekat ne sme biti jednak objektu druge klase");
    }

    @ParameterizedTest
    @DisplayName("equals treba ispravno da poredi dva objekta na osnovu pib, naziva firme i mejla")
    @CsvSource({
            "123456789, Firma DOO, firma@example.com, true",
            "999999999, Firma DOO, firma@example.com, false",
            "123456789, Druga Firma, firma@example.com, false",
            "123456789, Firma DOO, drugi@example.com, false"
    })
    void testEqualsKombinacije(String pib, String nazivFirme, String mejl, boolean ocekivano) {
        PravnoLice drugi = new PravnoLice(mejl, mesto, nazivFirme, pib);
        assertEquals(ocekivano, pravnoLice.equals(drugi),
                "equals rezultat se ne poklapa sa ocekivanim za kombinaciju: " + pib + ", " + nazivFirme + ", " + mejl);
    }

    @Test
    @DisplayName("hashCode treba da bude isti za objekte sa istim pib, nazivom firme i mejlom")
    void testHashCodeJednakiObjekti() {
        PravnoLice drugi = new PravnoLice("firma@example.com", mesto, "Firma DOO", "123456789");
        assertEquals(pravnoLice.hashCode(), drugi.hashCode(), "hashCode mora biti isti za jednake objekte");
    }

    @Test
    @DisplayName("hashCode treba da bude razlicit za objekte sa razlicitim pib")
    void testHashCodeRazlicitiObjekti() {
        PravnoLice drugi = new PravnoLice("firma@example.com", mesto, "Firma DOO", "999999999");
        assertNotEquals(pravnoLice.hashCode(), drugi.hashCode(), "hashCode treba da bude razlicit za razlicite objekte");
    }

    @Test
    @DisplayName("toString treba da vrati ocekivan format")
    void testToString() {
        assertEquals("PravnoLice{pib='123456789', nazivFirme='Firma DOO', mejl='firma@example.com'}",
                pravnoLice.toString(), "toString format se ne poklapa sa ocekivanim");
    }
}
