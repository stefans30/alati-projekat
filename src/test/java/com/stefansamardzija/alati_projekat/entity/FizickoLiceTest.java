package com.stefansamardzija.alati_projekat.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class FizickoLiceTest {

    private Mesto mesto;
    private FizickoLice fizickoLice;

    @BeforeEach
    void setUp() {
        mesto = new Mesto("Novi Sad");
        fizickoLice = new FizickoLice("pera@example.com", mesto, "Pera", "Peric", "0101990123456");
    }

    @AfterEach
    void tearDown() {
        mesto = null;
        fizickoLice = null;
    }

    @Test
    @DisplayName("Konstruktor bez argumenata treba da postavi sva polja na null")
    void testKonstruktorBezArgumenata() {
        FizickoLice prazno = new FizickoLice();
        assertAll("Sva polja moraju biti null nakon kreiranja praznim konstruktorom",
                () -> assertNull(prazno.getIdKupac(), "idKupac treba da bude null"),
                () -> assertNull(prazno.getMejl(), "mejl treba da bude null"),
                () -> assertNull(prazno.getMesto(), "mesto treba da bude null"),
                () -> assertNull(prazno.getIme(), "ime treba da bude null"),
                () -> assertNull(prazno.getPrezime(), "prezime treba da bude null"),
                () -> assertNull(prazno.getJmbg(), "jmbg treba da bude null")
        );
    }

    @Test
    @DisplayName("Konstruktor sa argumentima treba ispravno da postavi sva polja")
    void testKonstruktorSaArgumentima() {
        assertAll("Sva polja moraju biti postavljena na prosledjene vrednosti",
                () -> assertEquals("pera@example.com", fizickoLice.getMejl(), "mejl treba da bude 'pera@example.com'"),
                () -> assertEquals(mesto, fizickoLice.getMesto(), "mesto treba da bude prosledjeni objekat"),
                () -> assertEquals("Pera", fizickoLice.getIme(), "ime treba da bude 'Pera'"),
                () -> assertEquals("Peric", fizickoLice.getPrezime(), "prezime treba da bude 'Peric'"),
                () -> assertEquals("0101990123456", fizickoLice.getJmbg(), "jmbg treba da bude '0101990123456'"),
                () -> assertNull(fizickoLice.getIdKupac(), "idKupac treba da ostane null jer se ne postavlja kroz konstruktor")
        );
    }

    @Test
    @DisplayName("getTip treba da vrati 'fizicko'")
    void testGetTip() {
        assertEquals("fizicko", fizickoLice.getTip(), "getTip treba da vrati 'fizicko'");
    }

    @Test
    @DisplayName("setIdKupac treba da postavi vrednost idKupac polja")
    void testSetIdKupac() {
        fizickoLice.setIdKupac(7L);
        assertEquals(7L, fizickoLice.getIdKupac(), "idKupac treba da bude 7");
    }

    @Test
    @DisplayName("setMejl sa validnom vrednoscu treba uspesno da postavi mejl")
    void testSetMejlValidno() {
        fizickoLice.setMejl("novi@mejl.com");
        assertEquals("novi@mejl.com", fizickoLice.getMejl(), "mejl treba da bude 'novi@mejl.com'");
    }

    @Test
    @DisplayName("setMejl sa null vrednoscu treba da baci NullPointerException")
    void testSetMejlNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> fizickoLice.setMejl(null),
                "Ocekuje se NullPointerException kada je mejl null");
        assertEquals("Mejl ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @ParameterizedTest
    @DisplayName("setMejl sa nevalidnom vrednoscu (bez @) treba da baci IllegalArgumentException")
    @CsvSource({"peraexample.com", "''", "samotekst"})
    void testSetMejlNevalidno(String nevalidanMejl) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> fizickoLice.setMejl(nevalidanMejl),
                "Ocekuje se IllegalArgumentException za mejl bez '@': '" + nevalidanMejl + "'");
        assertEquals("Mejl mora sadrzati @", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @Test
    @DisplayName("setMesto sa validnom vrednoscu treba uspesno da postavi mesto")
    void testSetMestoValidno() {
        Mesto novoMesto = new Mesto("Beograd");
        fizickoLice.setMesto(novoMesto);
        assertEquals(novoMesto, fizickoLice.getMesto(), "mesto treba da bude 'Beograd'");
    }

    @Test
    @DisplayName("setMesto sa null vrednoscu treba da baci NullPointerException")
    void testSetMestoNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> fizickoLice.setMesto(null),
                "Ocekuje se NullPointerException kada je mesto null");
        assertEquals("Mesto ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @Test
    @DisplayName("setIme sa validnom vrednoscu treba uspesno da postavi ime")
    void testSetImeValidno() {
        fizickoLice.setIme("Mika");
        assertEquals("Mika", fizickoLice.getIme(), "ime treba da bude 'Mika'");
    }

    @Test
    @DisplayName("setIme sa null vrednoscu treba da baci NullPointerException")
    void testSetImeNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> fizickoLice.setIme(null),
                "Ocekuje se NullPointerException kada je ime null");
        assertEquals("Ime ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @ParameterizedTest
    @DisplayName("setIme sa nevalidnom (prekratkom) vrednoscu treba da baci IllegalArgumentException")
    @CsvSource({"''", "A"})
    void testSetImeNevalidno(String nevalidnoIme) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> fizickoLice.setIme(nevalidnoIme),
                "Ocekuje se IllegalArgumentException za ime kraci od 2 znaka: '" + nevalidnoIme + "'");
        assertEquals("Ime mora imati bar 2 znaka", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }


    @Test
    @DisplayName("setPrezime sa validnom vrednoscu treba uspesno da postavi prezime")
    void testSetPrezimeValidno() {
        fizickoLice.setPrezime("Jovanovic");
        assertEquals("Jovanovic", fizickoLice.getPrezime(), "prezime treba da bude 'Jovanovic'");
    }

    @Test
    @DisplayName("setPrezime sa null vrednoscu treba da baci NullPointerException")
    void testSetPrezimeNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> fizickoLice.setPrezime(null),
                "Ocekuje se NullPointerException kada je prezime null");
        assertEquals("Prezime ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @ParameterizedTest
    @DisplayName("setPrezime sa nevalidnom (prekratkom) vrednoscu treba da baci IllegalArgumentException")
    @CsvSource({"''", "A"})
    void testSetPrezimeNevalidno(String nevalidnoPrezime) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> fizickoLice.setPrezime(nevalidnoPrezime),
                "Ocekuje se IllegalArgumentException za prezime kraci od 2 znaka: '" + nevalidnoPrezime + "'");
        assertEquals("Prezime mora imati bar 2 znaka", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }


    @Test
    @DisplayName("setJmbg sa validnom vrednoscu treba uspesno da postavi jmbg")
    void testSetJmbgValidno() {
        fizickoLice.setJmbg("1234567890123");
        assertEquals("1234567890123", fizickoLice.getJmbg(), "jmbg treba da bude '1234567890123'");
    }

    @Test
    @DisplayName("setJmbg sa null vrednoscu treba da baci NullPointerException")
    void testSetJmbgNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> fizickoLice.setJmbg(null),
                "Ocekuje se NullPointerException kada je jmbg null");
        assertEquals("JMBG ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @ParameterizedTest
    @DisplayName("setJmbg sa nevalidnom vrednoscu treba da baci IllegalArgumentException")
    @CsvSource({"12345", "123456789012A", "12345678901234", "''"})
    void testSetJmbgNevalidno(String nevalidanJmbg) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> fizickoLice.setJmbg(nevalidanJmbg),
                "Ocekuje se IllegalArgumentException za jmbg koji ne sadrzi tacno 13 cifara: '" + nevalidanJmbg + "'");
        assertEquals("JMBG mora imati tacno 13 cifara", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @Test
    @DisplayName("equals treba da vrati true za isti objekat (refleksivnost)")
    void testEqualsRefleksivnost() {
        assertEquals(fizickoLice, fizickoLice, "Objekat mora biti jednak samom sebi");
    }

    @Test
    @DisplayName("equals treba da vrati false kada se poredi sa null")
    void testEqualsNull() {
        assertNotEquals(null, fizickoLice, "Objekat ne sme biti jednak null vrednosti");
    }

    @Test
    @DisplayName("equals treba da vrati false kada se poredi sa objektom druge klase")
    void testEqualsDrugaKlasa() {
        assertNotEquals(fizickoLice, "Pera", "Objekat ne sme biti jednak objektu druge klase");
    }

    @ParameterizedTest
    @DisplayName("equals treba ispravno da poredi dva objekta na osnovu jmbg, imena, prezimena i mejla")
    @CsvSource({
            "0101990123456, Pera, Peric, pera@example.com, true",
            "9999999999999, Pera, Peric, pera@example.com, false",
            "0101990123456, Mika, Peric, pera@example.com, false",
            "0101990123456, Pera, Jovanovic, pera@example.com, false",
            "0101990123456, Pera, Peric, drugi@example.com, false"
    })
    void testEqualsKombinacije(String jmbg, String ime, String prezime, String mejl, boolean ocekivano) {
        FizickoLice drugi = new FizickoLice(mejl, mesto, ime, prezime, jmbg);
        assertEquals(ocekivano, fizickoLice.equals(drugi),
                "equals rezultat se ne poklapa sa ocekivanim za kombinaciju: " + jmbg + ", " + ime + ", " + prezime + ", " + mejl);
    }

    @Test
    @DisplayName("hashCode treba da bude isti za objekte sa istim jmbg, imenom, prezimenom i mejlom")
    void testHashCodeJednakiObjekti() {
        FizickoLice drugi = new FizickoLice("pera@example.com", mesto, "Pera", "Peric", "0101990123456");
        assertEquals(fizickoLice.hashCode(), drugi.hashCode(), "hashCode mora biti isti za jednake objekte");
    }

    @Test
    @DisplayName("hashCode treba da bude razlicit za objekte sa razlicitim jmbg")
    void testHashCodeRazlicitiObjekti() {
        FizickoLice drugi = new FizickoLice("pera@example.com", mesto, "Pera", "Peric", "9999999999999");
        assertNotEquals(fizickoLice.hashCode(), drugi.hashCode(), "hashCode treba da bude razlicit za razlicite objekte");
    }

    @Test
    @DisplayName("toString treba da vrati ocekivan format")
    void testToString() {
        assertEquals("FizickoLice{jmbg='0101990123456', ime='Pera', prezime='Peric', mejl='pera@example.com'}",
                fizickoLice.toString(), "toString format se ne poklapa sa ocekivanim");
    }
}
