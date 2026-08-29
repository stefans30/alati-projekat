package com.stefansamardzija.alati_projekat.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StavkaRacunaTest {

    private Telefon telefon;
    private Prodavac prodavac;
    private Kupac kupac;
    private Racun racun;
    private StavkaRacuna stavkaRacuna;

    @BeforeEach
    void setUp() {
        telefon = new Telefon("iPhone 15", new BigDecimal("999.99"), "128GB, crna boja");
        prodavac = new Prodavac("Pera", "Peric", "pera@example.com", "pperic", "sifra123");
        kupac = new FizickoLice("kupac@example.com", new Mesto("Novi Sad"), "Mika", "Mikic", "0101990123456");
        racun = new Racun(LocalDate.of(2024, 5, 10), prodavac, kupac);
        stavkaRacuna = new StavkaRacuna(new BigDecimal("999.99"), 2, telefon);
    }

    @AfterEach
    void tearDown() {
        telefon = null;
        prodavac = null;
        kupac = null;
        racun = null;
        stavkaRacuna = null;
    }

    @Test
    @DisplayName("Konstruktor bez argumenata treba da postavi sva polja na null (osim kolicine koja je primitivna)")
    void testKonstruktorBezArgumenata() {
        StavkaRacuna prazna = new StavkaRacuna();
        assertAll("Sva referentna polja moraju biti null, a kolicina 0 nakon kreiranja praznim konstruktorom",
                () -> assertNull(prazna.getIdStavkaRacuna(), "idStavkaRacuna treba da bude null"),
                () -> assertEquals(0, prazna.getKolicina(), "kolicina (primitivni int) treba da bude 0"),
                () -> assertNull(prazna.getCena(), "cena treba da bude null"),
                () -> assertNull(prazna.getIznos(), "iznos treba da bude null"),
                () -> assertNull(prazna.getTelefon(), "telefon treba da bude null"),
                () -> assertNull(prazna.getRacun(), "racun treba da bude null")
        );
    }

    @Test
    @DisplayName("Konstruktor sa argumentima treba ispravno da postavi polja i izracuna iznos")
    void testKonstruktorSaArgumentima() {
        assertAll("Sva polja moraju biti postavljena, a iznos automatski izracunat",
                () -> assertEquals(new BigDecimal("999.99"), stavkaRacuna.getCena(), "cena treba da bude 999.99"),
                () -> assertEquals(2, stavkaRacuna.getKolicina(), "kolicina treba da bude 2"),
                () -> assertEquals(telefon, stavkaRacuna.getTelefon(), "telefon treba da bude prosledjeni objekat"),
                () -> assertEquals(new BigDecimal("1999.98"), stavkaRacuna.getIznos(), "iznos treba da bude cena * kolicina = 1999.98"),
                () -> assertNull(stavkaRacuna.getIdStavkaRacuna(), "idStavkaRacuna treba da ostane null jer se ne postavlja kroz konstruktor"),
                () -> assertNull(stavkaRacuna.getRacun(), "racun treba da ostane null jer se ne postavlja kroz konstruktor")
        );
    }


    @Test
    @DisplayName("setKolicina sa validnom vrednoscu treba uspesno da postavi kolicinu")
    void testSetKolicinaValidno() {
        stavkaRacuna.setKolicina(5);
        assertEquals(5, stavkaRacuna.getKolicina(), "kolicina treba da bude 5");
    }

    @ParameterizedTest
    @DisplayName("setKolicina sa nevalidnom vrednoscu (<=0) treba da baci IllegalArgumentException")
    @CsvSource({"0", "-1", "-100"})
    void testSetKolicinaNevalidno(int nevalidnaKolicina) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> stavkaRacuna.setKolicina(nevalidnaKolicina),
                "Ocekuje se IllegalArgumentException za kolicinu manju ili jednaku 0: " + nevalidnaKolicina);
        assertEquals("Kolicina mora biti veca od 0", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @Test
    @DisplayName("setCena sa validnom vrednoscu treba uspesno da postavi cenu")
    void testSetCenaValidno() {
        stavkaRacuna.setCena(new BigDecimal("500.00"));
        assertEquals(new BigDecimal("500.00"), stavkaRacuna.getCena(), "cena treba da bude 500.00");
    }

    @Test
    @DisplayName("setCena sa null vrednoscu treba da baci NullPointerException")
    void testSetCenaNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> stavkaRacuna.setCena(null),
                "Ocekuje se NullPointerException kada je cena null");
        assertEquals("Cena ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @ParameterizedTest
    @DisplayName("setCena sa nevalidnom vrednoscu (<=0) treba da baci IllegalArgumentException")
    @CsvSource({"0", "-1", "-50.25"})
    void testSetCenaNevalidno(String nevalidnaCena) {
        BigDecimal cena = new BigDecimal(nevalidnaCena);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> stavkaRacuna.setCena(cena),
                "Ocekuje se IllegalArgumentException za cenu manju ili jednaku 0: '" + nevalidnaCena + "'");
        assertEquals("Cena mora biti veca od 0", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }


    @Test
    @DisplayName("setIznos sa validnom vrednoscu treba uspesno da postavi iznos")
    void testSetIznosValidno() {
        stavkaRacuna.setIznos(new BigDecimal("2500.00"));
        assertEquals(new BigDecimal("2500.00"), stavkaRacuna.getIznos(), "iznos treba da bude 2500.00");
    }

    @Test
    @DisplayName("setIznos sa null vrednoscu treba da baci NullPointerException")
    void testSetIznosNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> stavkaRacuna.setIznos(null),
                "Ocekuje se NullPointerException kada je iznos null");
        assertEquals("Iznos ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @ParameterizedTest
    @DisplayName("setIznos sa nevalidnom vrednoscu (<=0) treba da baci IllegalArgumentException")
    @CsvSource({"0", "-1", "-75.10"})
    void testSetIznosNevalidno(String nevalidanIznos) {
        BigDecimal iznos = new BigDecimal(nevalidanIznos);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> stavkaRacuna.setIznos(iznos),
                "Ocekuje se IllegalArgumentException za iznos manji ili jednak 0: '" + nevalidanIznos + "'");
        assertEquals("Iznos mora biti veci od 0", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }


    @Test
    @DisplayName("setTelefon sa validnom vrednoscu treba uspesno da postavi telefon")
    void testSetTelefonValidno() {
        Telefon drugiTelefon = new Telefon("Samsung Galaxy S24", new BigDecimal("899.00"), null);
        stavkaRacuna.setTelefon(drugiTelefon);
        assertEquals(drugiTelefon, stavkaRacuna.getTelefon(), "telefon treba da bude drugi telefon");
    }

    @Test
    @DisplayName("setTelefon sa null vrednoscu treba da baci NullPointerException")
    void testSetTelefonNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> stavkaRacuna.setTelefon(null),
                "Ocekuje se NullPointerException kada je telefon null");
        assertEquals("Telefon ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @Test
    @DisplayName("setRacun sa validnom vrednoscu treba uspesno da postavi racun")
    void testSetRacunValidno() {
        stavkaRacuna.setRacun(racun);
        assertEquals(racun, stavkaRacuna.getRacun(), "racun treba da bude prosledjeni objekat");
    }

    @Test
    @DisplayName("setRacun sa null vrednoscu treba da baci NullPointerException")
    void testSetRacunNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> stavkaRacuna.setRacun(null),
                "Ocekuje se NullPointerException kada je racun null");
        assertEquals("Racun ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @Test
    @DisplayName("preracunajIznos treba ponovo da izracuna iznos na osnovu cene i kolicine")
    void testPreracunajIznos() {
        stavkaRacuna.setKolicina(3);
        stavkaRacuna.preracunajIznos();
        assertEquals(new BigDecimal("2999.97"), stavkaRacuna.getIznos(),
                "iznos treba da bude ponovo izracunat kao cena * kolicina = 2999.97");
    }

    @Test
    @DisplayName("equals treba da vrati true za isti objekat (refleksivnost)")
    void testEqualsRefleksivnost() {
        assertEquals(stavkaRacuna, stavkaRacuna, "Objekat mora biti jednak samom sebi");
    }

    @Test
    @DisplayName("equals treba da vrati false kada se poredi sa null")
    void testEqualsNull() {
        assertNotEquals(null, stavkaRacuna, "Objekat ne sme biti jednak null vrednosti");
    }

    @Test
    @DisplayName("equals treba da vrati false kada se poredi sa objektom druge klase")
    void testEqualsDrugaKlasa() {
        assertNotEquals(stavkaRacuna, "StavkaRacuna", "Objekat ne sme biti jednak objektu druge klase");
    }

    @Test
    @DisplayName("equals treba da vrati true za dve stavke sa istom kolicinom, telefonom, cenom i iznosom")
    void testEqualsJednakiObjekti() {
        StavkaRacuna druga = new StavkaRacuna(new BigDecimal("999.99"), 2, telefon);
        assertEquals(stavkaRacuna, druga, "Stavke sa istim poljima treba da budu jednake");
    }

    @Test
    @DisplayName("equals treba da vrati false za stavke sa razlicitom kolicinom (samim tim i razlicitim iznosom)")
    void testEqualsRazlicitaKolicina() {
        StavkaRacuna druga = new StavkaRacuna(new BigDecimal("999.99"), 3, telefon);
        assertNotEquals(stavkaRacuna, druga, "Stavke sa razlicitom kolicinom ne treba da budu jednake");
    }

    @Test
    @DisplayName("equals treba da vrati false za stavke sa razlicitim telefonom")
    void testEqualsRazlicitTelefon() {
        Telefon drugiTelefon = new Telefon("Samsung Galaxy S24", new BigDecimal("999.99"), null);
        StavkaRacuna druga = new StavkaRacuna(new BigDecimal("999.99"), 2, drugiTelefon);
        assertNotEquals(stavkaRacuna, druga, "Stavke sa razlicitim telefonom ne treba da budu jednake");
    }

    @Test
    @DisplayName("hashCode treba da bude isti za jednake stavke")
    void testHashCodeJednakiObjekti() {
        StavkaRacuna druga = new StavkaRacuna(new BigDecimal("999.99"), 2, telefon);
        assertEquals(stavkaRacuna.hashCode(), druga.hashCode(), "hashCode mora biti isti za jednake objekte");
    }

    @Test
    @DisplayName("hashCode treba da bude razlicit za stavke sa razlicitom kolicinom")
    void testHashCodeRazlicitiObjekti() {
        StavkaRacuna druga = new StavkaRacuna(new BigDecimal("999.99"), 3, telefon);
        assertNotEquals(stavkaRacuna.hashCode(), druga.hashCode(), "hashCode treba da bude razlicit za razlicite objekte");
    }

    @Test
    @DisplayName("toString treba da vrati ocekivan format")
    void testToString() {
        assertEquals("StavkaRacuna{telefon=" + telefon + ", kolicina=2, cena=999.99, iznos=1999.98}",
                stavkaRacuna.toString(), "toString format se ne poklapa sa ocekivanim");
    }
}
