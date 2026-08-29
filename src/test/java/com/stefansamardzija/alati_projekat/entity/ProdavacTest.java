package com.stefansamardzija.alati_projekat.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProdavacTest {

    private Prodavac prodavac;

    @BeforeEach
    void setUp() {
        prodavac = new Prodavac("Pera", "Peric", "pera@example.com", "pperic", "sifra123");
    }

    @AfterEach
    void tearDown() {
        prodavac = null;
    }

    @Test
    @DisplayName("Konstruktor bez argumenata treba da postavi sva polja na null, a listu sertifikata na praznu")
    void testKonstruktorBezArgumenata() {
        Prodavac prazan = new Prodavac();
        assertAll("Sva polja moraju biti null (osim liste sertifikata) nakon kreiranja praznim konstruktorom",
                () -> assertNull(prazan.getIdProdavac(), "idProdavac treba da bude null"),
                () -> assertNull(prazan.getIme(), "ime treba da bude null"),
                () -> assertNull(prazan.getPrezime(), "prezime treba da bude null"),
                () -> assertNull(prazan.getMejl(), "mejl treba da bude null"),
                () -> assertNull(prazan.getKorisnickoIme(), "korisnickoIme treba da bude null"),
                () -> assertNull(prazan.getSifra(), "sifra treba da bude null"),
                () -> assertNotNull(prazan.getSertifikati(), "sertifikati ne sme biti null (podrazumevano prazna lista)"),
                () -> assertTrue(prazan.getSertifikati().isEmpty(), "sertifikati treba da bude prazna lista")
        );
    }

    @Test
    @DisplayName("Konstruktor sa argumentima treba ispravno da postavi sva polja")
    void testKonstruktorSaArgumentima() {
        assertAll("Sva polja moraju biti postavljena na prosledjene vrednosti",
                () -> assertEquals("Pera", prodavac.getIme(), "ime treba da bude 'Pera'"),
                () -> assertEquals("Peric", prodavac.getPrezime(), "prezime treba da bude 'Peric'"),
                () -> assertEquals("pera@example.com", prodavac.getMejl(), "mejl treba da bude 'pera@example.com'"),
                () -> assertEquals("pperic", prodavac.getKorisnickoIme(), "korisnickoIme treba da bude 'pperic'"),
                () -> assertEquals("sifra123", prodavac.getSifra(), "sifra treba da bude 'sifra123'"),
                () -> assertNull(prodavac.getIdProdavac(), "idProdavac treba da ostane null jer se ne postavlja kroz konstruktor")
        );
    }


    @Test
    @DisplayName("setIme sa validnom vrednoscu treba uspesno da postavi ime")
    void testSetImeValidno() {
        prodavac.setIme("Mika");
        assertEquals("Mika", prodavac.getIme(), "ime treba da bude 'Mika'");
    }

    @Test
    @DisplayName("setIme sa null vrednoscu treba da baci NullPointerException")
    void testSetImeNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> prodavac.setIme(null),
                "Ocekuje se NullPointerException kada je ime null");
        assertEquals("Ime ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @ParameterizedTest
    @DisplayName("setIme sa nevalidnom (prekratkom) vrednoscu treba da baci IllegalArgumentException")
    @CsvSource({"''", "A"})
    void testSetImeNevalidno(String nevalidnoIme) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> prodavac.setIme(nevalidnoIme),
                "Ocekuje se IllegalArgumentException za ime kraci od 2 znaka: '" + nevalidnoIme + "'");
        assertEquals("Ime mora imati bar 2 znaka", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }


    @Test
    @DisplayName("setPrezime sa validnom vrednoscu treba uspesno da postavi prezime")
    void testSetPrezimeValidno() {
        prodavac.setPrezime("Jovanovic");
        assertEquals("Jovanovic", prodavac.getPrezime(), "prezime treba da bude 'Jovanovic'");
    }

    @Test
    @DisplayName("setPrezime sa null vrednoscu treba da baci NullPointerException")
    void testSetPrezimeNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> prodavac.setPrezime(null),
                "Ocekuje se NullPointerException kada je prezime null");
        assertEquals("Prezime ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @ParameterizedTest
    @DisplayName("setPrezime sa nevalidnom (prekratkom) vrednoscu treba da baci IllegalArgumentException")
    @CsvSource({"''", "A"})
    void testSetPrezimeNevalidno(String nevalidnoPrezime) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> prodavac.setPrezime(nevalidnoPrezime),
                "Ocekuje se IllegalArgumentException za prezime kraci od 2 znaka: '" + nevalidnoPrezime + "'");
        assertEquals("Prezime mora imati bar 2 znaka", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @Test
    @DisplayName("setMejl sa validnom vrednoscu treba uspesno da postavi mejl")
    void testSetMejlValidno() {
        prodavac.setMejl("novi@mejl.com");
        assertEquals("novi@mejl.com", prodavac.getMejl(), "mejl treba da bude 'novi@mejl.com'");
    }

    @Test
    @DisplayName("setMejl sa null vrednoscu treba da baci NullPointerException")
    void testSetMejlNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> prodavac.setMejl(null),
                "Ocekuje se NullPointerException kada je mejl null");
        assertEquals("Mejl ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @ParameterizedTest
    @DisplayName("setMejl sa nevalidnom vrednoscu (bez @) treba da baci IllegalArgumentException")
    @CsvSource({"peraexample.com", "''", "samotekst"})
    void testSetMejlNevalidno(String nevalidanMejl) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> prodavac.setMejl(nevalidanMejl),
                "Ocekuje se IllegalArgumentException za mejl bez '@': '" + nevalidanMejl + "'");
        assertEquals("Mejl mora sadrzati @", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }


    @Test
    @DisplayName("setKorisnickoIme sa validnom vrednoscu treba uspesno da postavi korisnicko ime")
    void testSetKorisnickoImeValidno() {
        prodavac.setKorisnickoIme("novikor");
        assertEquals("novikor", prodavac.getKorisnickoIme(), "korisnickoIme treba da bude 'novikor'");
    }

    @Test
    @DisplayName("setKorisnickoIme sa null vrednoscu treba da baci NullPointerException")
    void testSetKorisnickoImeNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> prodavac.setKorisnickoIme(null),
                "Ocekuje se NullPointerException kada je korisnicko ime null");
        assertEquals("Korisnicko ime ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @ParameterizedTest
    @DisplayName("setKorisnickoIme sa nevalidnom (prekratkom) vrednoscu treba da baci IllegalArgumentException")
    @CsvSource({"''", "a", "ab", "abc"})
    void testSetKorisnickoImeNevalidno(String nevalidnoKorIme) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> prodavac.setKorisnickoIme(nevalidnoKorIme),
                "Ocekuje se IllegalArgumentException za korisnicko ime kraci od 4 znaka: '" + nevalidnoKorIme + "'");
        assertEquals("Korisnicko ime mora imati bar 4 znaka", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @Test
    @DisplayName("setSifra sa validnom vrednoscu treba uspesno da postavi sifru")
    void testSetSifraValidno() {
        prodavac.setSifra("novasifra");
        assertEquals("novasifra", prodavac.getSifra(), "sifra treba da bude 'novasifra'");
    }

    @Test
    @DisplayName("setSifra sa null vrednoscu treba da baci NullPointerException")
    void testSetSifraNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> prodavac.setSifra(null),
                "Ocekuje se NullPointerException kada je sifra null");
        assertEquals("Sifra ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @ParameterizedTest
    @DisplayName("setSifra sa nevalidnom (prekratkom) vrednoscu treba da baci IllegalArgumentException")
    @CsvSource({"''", "a", "ab", "abc", "abcd", "abcde"})
    void testSetSifraNevalidno(String nevalidnaSifra) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> prodavac.setSifra(nevalidnaSifra),
                "Ocekuje se IllegalArgumentException za sifru kracu od 6 znakova: '" + nevalidnaSifra + "'");
        assertEquals("Sifra mora imati bar 6 znakova", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @Test
    @DisplayName("setSertifikati treba da postavi novu listu sertifikata")
    void testSetSertifikati() {
        List<ProdavacSertifikat> novaLista = new ArrayList<>();
        prodavac.setSertifikati(novaLista);
        assertSame(novaLista, prodavac.getSertifikati(), "sertifikati treba da bude tacno prosledjena lista");
    }

    @Test
    @DisplayName("equals treba da vrati true za isti objekat (refleksivnost)")
    void testEqualsRefleksivnost() {
        assertEquals(prodavac, prodavac, "Objekat mora biti jednak samom sebi");
    }

    @Test
    @DisplayName("equals treba da vrati false kada se poredi sa null")
    void testEqualsNull() {
        assertNotEquals(null, prodavac, "Objekat ne sme biti jednak null vrednosti");
    }

    @Test
    @DisplayName("equals treba da vrati false kada se poredi sa objektom druge klase")
    void testEqualsDrugaKlasa() {
        assertNotEquals(prodavac, "Pera", "Objekat ne sme biti jednak objektu druge klase");
    }

    @ParameterizedTest
    @DisplayName("equals treba ispravno da poredi dva objekta na osnovu korisnickog imena, imena i prezimena")
    @CsvSource({
            "pperic, Pera, Peric, true",
            "drugikor, Pera, Peric, false",
            "pperic, Mika, Peric, false",
            "pperic, Pera, Jovanovic, false"
    })
    void testEqualsKombinacije(String korisnickoIme, String ime, String prezime, boolean ocekivano) {
        Prodavac drugi = new Prodavac(ime, prezime, "neki@mejl.com", korisnickoIme, "sifra123");
        assertEquals(ocekivano, prodavac.equals(drugi),
                "equals rezultat se ne poklapa sa ocekivanim za kombinaciju: " + korisnickoIme + ", " + ime + ", " + prezime);
    }

    @Test
    @DisplayName("hashCode treba da bude isti za objekte sa istim korisnickim imenom, imenom i prezimenom")
    void testHashCodeJednakiObjekti() {
        Prodavac drugi = new Prodavac("Pera", "Peric", "drugi@mejl.com", "pperic", "drugasifra");
        assertEquals(prodavac.hashCode(), drugi.hashCode(), "hashCode mora biti isti za jednake objekte");
    }

    @Test
    @DisplayName("hashCode treba da bude razlicit za objekte sa razlicitim korisnickim imenom")
    void testHashCodeRazlicitiObjekti() {
        Prodavac drugi = new Prodavac("Pera", "Peric", "pera@example.com", "drugikor", "sifra123");
        assertNotEquals(prodavac.hashCode(), drugi.hashCode(), "hashCode treba da bude razlicit za razlicite objekte");
    }

    @Test
    @DisplayName("toString treba da vrati ocekivan format")
    void testToString() {
        assertEquals("Prodavac{korisnickoIme='pperic', ime='Pera', prezime='Peric'}",
                prodavac.toString(), "toString format se ne poklapa sa ocekivanim");
    }
}
