package com.stefansamardzija.alati_projekat.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RacunTest {

    private Prodavac prodavac;
    private Kupac kupac;
    private LocalDate datum;
    private Racun racun;

    @BeforeEach
    void setUp() {
        prodavac = new Prodavac("Pera", "Peric", "pera@example.com", "pperic", "sifra123");
        kupac = new FizickoLice("kupac@example.com", new Mesto("Novi Sad"), "Mika", "Mikic", "0101990123456");
        datum = LocalDate.of(2024, 5, 10);
        racun = new Racun(datum, prodavac, kupac);
    }

    @AfterEach
    void tearDown() {
        prodavac = null;
        kupac = null;
        datum = null;
        racun = null;
    }

    @Test
    @DisplayName("Konstruktor bez argumenata treba da postavi referentna polja na null, ukupanIznos na nulu, a stavke na praznu listu")
    void testKonstruktorBezArgumenata() {
        Racun prazan = new Racun();
        assertAll("Podrazumevane vrednosti nakon kreiranja praznim konstruktorom",
                () -> assertNull(prazan.getIdRacun(), "idRacun treba da bude null"),
                () -> assertNull(prazan.getDatum(), "datum treba da bude null"),
                () -> assertEquals(BigDecimal.ZERO, prazan.getUkupanIznos(), "ukupanIznos treba da bude podrazumevano BigDecimal.ZERO"),
                () -> assertNull(prazan.getProdavac(), "prodavac treba da bude null"),
                () -> assertNull(prazan.getKupac(), "kupac treba da bude null"),
                () -> assertNotNull(prazan.getStavke(), "stavke ne sme biti null"),
                () -> assertTrue(prazan.getStavke().isEmpty(), "stavke treba da bude prazna lista")
        );
    }

    @Test
    @DisplayName("Konstruktor sa argumentima treba ispravno da postavi datum, prodavca i kupca")
    void testKonstruktorSaArgumentima() {
        assertAll("Sva polja moraju biti postavljena na prosledjene vrednosti",
                () -> assertEquals(datum, racun.getDatum(), "datum treba da bude prosledjena vrednost"),
                () -> assertEquals(prodavac, racun.getProdavac(), "prodavac treba da bude prosledjeni objekat"),
                () -> assertEquals(kupac, racun.getKupac(), "kupac treba da bude prosledjeni objekat"),
                () -> assertEquals(BigDecimal.ZERO, racun.getUkupanIznos(), "ukupanIznos treba da bude podrazumevano BigDecimal.ZERO"),
                () -> assertNull(racun.getIdRacun(), "idRacun treba da ostane null jer se ne postavlja kroz konstruktor"),
                () -> assertTrue(racun.getStavke().isEmpty(), "stavke treba da bude prazna lista")
        );
    }


    @Test
    @DisplayName("setDatum sa validnom vrednoscu treba uspesno da postavi datum")
    void testSetDatumValidno() {
        LocalDate noviDatum = LocalDate.of(2025, 1, 1);
        racun.setDatum(noviDatum);
        assertEquals(noviDatum, racun.getDatum(), "datum treba da bude novi datum");
    }

    @Test
    @DisplayName("setDatum sa null vrednoscu treba da baci NullPointerException")
    void testSetDatumNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> racun.setDatum(null),
                "Ocekuje se NullPointerException kada je datum null");
        assertEquals("Datum ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @Test
    @DisplayName("setUkupanIznos sa validnom vrednoscu treba uspesno da postavi ukupan iznos")
    void testSetUkupanIznosValidno() {
        racun.setUkupanIznos(new BigDecimal("1500.00"));
        assertEquals(new BigDecimal("1500.00"), racun.getUkupanIznos(), "ukupanIznos treba da bude 1500.00");
    }

    @Test
    @DisplayName("setUkupanIznos sa vrednoscu nula treba uspesno da prodje (dozvoljena granica)")
    void testSetUkupanIznosNula() {
        racun.setUkupanIznos(BigDecimal.ZERO);
        assertEquals(BigDecimal.ZERO, racun.getUkupanIznos(), "ukupanIznos treba da bude nula");
    }

    @Test
    @DisplayName("setUkupanIznos sa null vrednoscu treba da baci NullPointerException")
    void testSetUkupanIznosNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> racun.setUkupanIznos(null),
                "Ocekuje se NullPointerException kada je ukupan iznos null");
        assertEquals("Ukupan iznos ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @ParameterizedTest
    @DisplayName("setUkupanIznos sa negativnom vrednoscu treba da baci IllegalArgumentException")
    @CsvSource({"-1", "-100.50"})
    void testSetUkupanIznosNevalidno(String nevalidanIznos) {
        BigDecimal iznos = new BigDecimal(nevalidanIznos);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> racun.setUkupanIznos(iznos),
                "Ocekuje se IllegalArgumentException za negativan ukupan iznos: '" + nevalidanIznos + "'");
        assertEquals("Ukupan iznos ne sme biti negativan", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }


    @Test
    @DisplayName("setProdavac sa validnom vrednoscu treba uspesno da postavi prodavca")
    void testSetProdavacValidno() {
        Prodavac drugiProdavac = new Prodavac("Mika", "Jovic", "mika@example.com", "mjovic", "sifra456");
        racun.setProdavac(drugiProdavac);
        assertEquals(drugiProdavac, racun.getProdavac(), "prodavac treba da bude drugi prodavac");
    }

    @Test
    @DisplayName("setProdavac sa null vrednoscu treba da baci NullPointerException")
    void testSetProdavacNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> racun.setProdavac(null),
                "Ocekuje se NullPointerException kada je prodavac null");
        assertEquals("Prodavac ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @Test
    @DisplayName("setKupac sa validnom vrednoscu treba uspesno da postavi kupca")
    void testSetKupacValidno() {
        Kupac drugiKupac = new PravnoLice("firma@example.com", new Mesto("Beograd"), "Firma DOO", "123456789");
        racun.setKupac(drugiKupac);
        assertEquals(drugiKupac, racun.getKupac(), "kupac treba da bude drugi kupac");
    }

    @Test
    @DisplayName("setKupac sa null vrednoscu treba da baci NullPointerException")
    void testSetKupacNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> racun.setKupac(null),
                "Ocekuje se NullPointerException kada je kupac null");
        assertEquals("Kupac ne sme biti null", ex.getMessage(), "Poruka izuzetka treba da odgovara");
    }

    @Test
    @DisplayName("setStavke treba da postavi novu listu stavki")
    void testSetStavke() {
        List<StavkaRacuna> novaLista = new ArrayList<>();
        racun.setStavke(novaLista);
        assertSame(novaLista, racun.getStavke(), "stavke treba da bude tacno prosledjena lista");
    }

    @Test
    @DisplayName("dodajStavku treba da doda stavku u listu i postavi joj referencu na racun")
    void testDodajStavku() {
        Telefon telefon = new Telefon("iPhone 15", new BigDecimal("999.99"), null);
        StavkaRacuna stavka = new StavkaRacuna(new BigDecimal("999.99"), 2, telefon);

        racun.dodajStavku(stavka);

        assertAll("Stavka treba da bude dodata u listu, a njena referenca na racun postavljena",
                () -> assertEquals(1, racun.getStavke().size(), "Lista stavki treba da sadrzi tacno jednu stavku"),
                () -> assertSame(stavka, racun.getStavke().get(0), "Dodata stavka treba da bude ista referenca"),
                () -> assertSame(racun, stavka.getRacun(), "Stavci treba da bude postavljena referenca na ovaj racun")
        );
    }

    @Test
    @DisplayName("preracunajUkupanIznos treba da sabere iznose svih stavki")
    void testPreracunajUkupanIznos() {
        Telefon telefon1 = new Telefon("iPhone 15", new BigDecimal("999.99"), null);
        Telefon telefon2 = new Telefon("Samsung Galaxy S24", new BigDecimal("899.00"), null);
        racun.dodajStavku(new StavkaRacuna(new BigDecimal("999.99"), 2, telefon1));
        racun.dodajStavku(new StavkaRacuna(new BigDecimal("899.00"), 1, telefon2));

        racun.preracunajUkupanIznos();

        assertEquals(new BigDecimal("2898.98"), racun.getUkupanIznos(),
                "ukupanIznos treba da bude zbir iznosa svih stavki: 1999.98 + 899.00 = 2898.98");
    }

    @Test
    @DisplayName("preracunajUkupanIznos na racunu bez stavki treba da postavi ukupanIznos na nulu")
    void testPreracunajUkupanIznosBezStavki() {
        racun.preracunajUkupanIznos();
        assertEquals(BigDecimal.ZERO, racun.getUkupanIznos(), "ukupanIznos treba da bude nula kada nema stavki");
    }
}
