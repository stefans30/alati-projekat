package com.stefansamardzija.alati_projekat.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.stefansamardzija.alati_projekat.dto.KreirajRacunZahtev;
import com.stefansamardzija.alati_projekat.dto.StavkaZahtev;
import com.stefansamardzija.alati_projekat.entity.FizickoLice;
import com.stefansamardzija.alati_projekat.entity.Kupac;
import com.stefansamardzija.alati_projekat.entity.Mesto;
import com.stefansamardzija.alati_projekat.entity.Prodavac;
import com.stefansamardzija.alati_projekat.entity.Racun;
import com.stefansamardzija.alati_projekat.entity.Telefon;
import com.stefansamardzija.alati_projekat.repository.KupacRepository;
import com.stefansamardzija.alati_projekat.repository.ProdavacRepository;
import com.stefansamardzija.alati_projekat.repository.RacunRepository;
import com.stefansamardzija.alati_projekat.repository.TelefonRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RacunServisImplTest {

    @Mock
    private RacunRepository racunRepository;

    @Mock
    private ProdavacRepository prodavacRepository;

    @Mock
    private KupacRepository kupacRepository;

    @Mock
    private TelefonRepository telefonRepository;

    @InjectMocks
    private RacunServisImpl racunServis;

    private Prodavac prodavac;
    private Kupac kupac;
    private Telefon telefon;

    @BeforeEach
    void setUp() {
        prodavac = new Prodavac("Pera", "Peric", "pera@mail.com", "pperic", "tajna123");
        prodavac.setIdProdavac(1L);
        kupac = new FizickoLice("mika@mail.com", new Mesto("Novi Sad"), "Mika", "Mikic", "1234567890123");
        kupac.setIdKupac(2L);
        telefon = new Telefon("iPhone 15", new BigDecimal("999.99"), "128GB");
        telefon.setIdTelefon(3L);
    }

    @Test
    @DisplayName("kreirajRacun - null zahtev - baca NullPointerException")
    void kreirajRacun_nullZahtev_bacaNullPointerException() {
        assertThrows(NullPointerException.class, () -> racunServis.kreirajRacun(null));
        verify(prodavacRepository, never()).findById(any());
        verify(racunRepository, never()).save(any());
    }

    @Test
    @DisplayName("kreirajRacun - prodavac ne postoji - baca ResponseStatusException NOT_FOUND")
    void kreirajRacun_prodavacNePostoji_bacaNotFound() {
        KreirajRacunZahtev zahtev = new KreirajRacunZahtev(1L, 2L,
                List.of(new StavkaZahtev(3L, 1, null)));
        when(prodavacRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> racunServis.kreirajRacun(zahtev));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(kupacRepository, never()).findById(any());
        verify(racunRepository, never()).save(any());
    }

    @Test
    @DisplayName("kreirajRacun - kupac ne postoji - baca ResponseStatusException NOT_FOUND")
    void kreirajRacun_kupacNePostoji_bacaNotFound() {
        KreirajRacunZahtev zahtev = new KreirajRacunZahtev(1L, 2L,
                List.of(new StavkaZahtev(3L, 1, null)));
        when(prodavacRepository.findById(1L)).thenReturn(Optional.of(prodavac));
        when(kupacRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> racunServis.kreirajRacun(zahtev));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(telefonRepository, never()).findById(any());
        verify(racunRepository, never()).save(any());
    }

    @Test
    @DisplayName("kreirajRacun - lista stavki je null - baca ResponseStatusException BAD_REQUEST")
    void kreirajRacun_stavkeNull_bacaBadRequest() {
        KreirajRacunZahtev zahtev = new KreirajRacunZahtev(1L, 2L, null);
        when(prodavacRepository.findById(1L)).thenReturn(Optional.of(prodavac));
        when(kupacRepository.findById(2L)).thenReturn(Optional.of(kupac));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> racunServis.kreirajRacun(zahtev));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        verify(racunRepository, never()).save(any());
    }

    @Test
    @DisplayName("kreirajRacun - lista stavki je prazna - baca ResponseStatusException BAD_REQUEST")
    void kreirajRacun_stavkePrazneListe_bacaBadRequest() {
        KreirajRacunZahtev zahtev = new KreirajRacunZahtev(1L, 2L, List.of());
        when(prodavacRepository.findById(1L)).thenReturn(Optional.of(prodavac));
        when(kupacRepository.findById(2L)).thenReturn(Optional.of(kupac));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> racunServis.kreirajRacun(zahtev));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        verify(racunRepository, never()).save(any());
    }

    @Test
    @DisplayName("kreirajRacun - telefon iz stavke ne postoji - baca ResponseStatusException NOT_FOUND")
    void kreirajRacun_telefonNePostoji_bacaNotFound() {
        KreirajRacunZahtev zahtev = new KreirajRacunZahtev(1L, 2L,
                List.of(new StavkaZahtev(3L, 1, null)));
        when(prodavacRepository.findById(1L)).thenReturn(Optional.of(prodavac));
        when(kupacRepository.findById(2L)).thenReturn(Optional.of(kupac));
        when(telefonRepository.findById(3L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> racunServis.kreirajRacun(zahtev));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(racunRepository, never()).save(any());
    }

    @Test
    @DisplayName("kreirajRacun - stavka bez zadate cene - koristi cenu telefona kao default")
    void kreirajRacun_stavkaBezCene_koristiCenuTelefona() {
        KreirajRacunZahtev zahtev = new KreirajRacunZahtev(1L, 2L,
                List.of(new StavkaZahtev(3L, 2, null)));
        when(prodavacRepository.findById(1L)).thenReturn(Optional.of(prodavac));
        when(kupacRepository.findById(2L)).thenReturn(Optional.of(kupac));
        when(telefonRepository.findById(3L)).thenReturn(Optional.of(telefon));
        when(racunRepository.save(any(Racun.class))).thenAnswer(inv -> inv.getArgument(0));

        Racun rezultat = racunServis.kreirajRacun(zahtev);

        ArgumentCaptor<Racun> captor = ArgumentCaptor.forClass(Racun.class);
        verify(racunRepository, times(1)).save(captor.capture());
        Racun sacuvan = captor.getValue();
        assertEquals(1, sacuvan.getStavke().size());
        assertEquals(telefon.getCena(), sacuvan.getStavke().get(0).getCena(),
                "cena stavke treba da bude preuzeta sa telefona kada nije eksplicitno zadata");
        assertEquals(new BigDecimal("1999.98"), sacuvan.getUkupanIznos());
        assertSame(sacuvan, rezultat);
    }

    @Test
    @DisplayName("kreirajRacun - stavka sa zadatom cenom - koristi zadatu cenu umesto cene telefona")
    void kreirajRacun_stavkaSaCenom_koristiZadatuCenu() {
        BigDecimal zadataCena = new BigDecimal("500.00");
        KreirajRacunZahtev zahtev = new KreirajRacunZahtev(1L, 2L,
                List.of(new StavkaZahtev(3L, 1, zadataCena)));
        when(prodavacRepository.findById(1L)).thenReturn(Optional.of(prodavac));
        when(kupacRepository.findById(2L)).thenReturn(Optional.of(kupac));
        when(telefonRepository.findById(3L)).thenReturn(Optional.of(telefon));
        when(racunRepository.save(any(Racun.class))).thenAnswer(inv -> inv.getArgument(0));

        Racun rezultat = racunServis.kreirajRacun(zahtev);

        ArgumentCaptor<Racun> captor = ArgumentCaptor.forClass(Racun.class);
        verify(racunRepository, times(1)).save(captor.capture());
        Racun sacuvan = captor.getValue();
        assertEquals(zadataCena, sacuvan.getStavke().get(0).getCena(),
                "cena stavke treba da bude eksplicitno zadata cena, ne cena telefona");
        assertEquals(zadataCena, sacuvan.getUkupanIznos());
        assertSame(sacuvan, rezultat);
    }

    @Test
    @DisplayName("kreirajRacun - vise stavki - kreira racun sa svim stavkama i tacnim ukupnim iznosom")
    void kreirajRacun_viseStavki_kreiraRacunSaSvimStavkama() {
        Telefon telefon2 = new Telefon("Samsung Galaxy S24", new BigDecimal("800.00"), "256GB");
        telefon2.setIdTelefon(4L);
        KreirajRacunZahtev zahtev = new KreirajRacunZahtev(1L, 2L, List.of(
                new StavkaZahtev(3L, 1, null),
                new StavkaZahtev(4L, 2, new BigDecimal("750.00"))
        ));
        when(prodavacRepository.findById(1L)).thenReturn(Optional.of(prodavac));
        when(kupacRepository.findById(2L)).thenReturn(Optional.of(kupac));
        when(telefonRepository.findById(3L)).thenReturn(Optional.of(telefon));
        when(telefonRepository.findById(4L)).thenReturn(Optional.of(telefon2));
        when(racunRepository.save(any(Racun.class))).thenAnswer(inv -> inv.getArgument(0));

        Racun rezultat = racunServis.kreirajRacun(zahtev);

        ArgumentCaptor<Racun> captor = ArgumentCaptor.forClass(Racun.class);
        verify(racunRepository, times(1)).save(captor.capture());
        Racun sacuvan = captor.getValue();
        assertEquals(2, sacuvan.getStavke().size());
        assertEquals(prodavac, sacuvan.getProdavac());
        assertEquals(kupac, sacuvan.getKupac());
        // 999.99 * 1 + 750.00 * 2 = 2499.99
        assertEquals(new BigDecimal("2499.99"), sacuvan.getUkupanIznos());
        assertSame(sacuvan, rezultat);
    }

    @Test
    @DisplayName("pretraziRacune - postoje racuni - vraca sve racune")
    void pretraziRacune_postojeRacuni_vracaSveRacune() {
        Racun racun = new Racun(java.time.LocalDate.now(), prodavac, kupac);
        List<Racun> svi = List.of(racun);
        when(racunRepository.findAll()).thenReturn(svi);

        List<Racun> rezultat = racunServis.pretraziRacune();

        assertEquals(svi, rezultat);
        verify(racunRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("vratiRacun - null id - baca NullPointerException")
    void vratiRacun_nullId_bacaNullPointerException() {
        assertThrows(NullPointerException.class, () -> racunServis.vratiRacun(null));
        verify(racunRepository, never()).findById(any());
    }

    @Test
    @DisplayName("vratiRacun - racun ne postoji - baca ResponseStatusException NOT_FOUND")
    void vratiRacun_racunNePostoji_bacaNotFound() {
        when(racunRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> racunServis.vratiRacun(1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    @DisplayName("vratiRacun - racun postoji - vraca racun")
    void vratiRacun_racunPostoji_vracaRacun() {
        Racun racun = new Racun(java.time.LocalDate.now(), prodavac, kupac);
        racun.setIdRacun(1L);
        when(racunRepository.findById(1L)).thenReturn(Optional.of(racun));

        Racun rezultat = racunServis.vratiRacun(1L);

        assertEquals(racun, rezultat);
        verify(racunRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("racuniZaProdavca - null idProdavac - baca NullPointerException")
    void racuniZaProdavca_nullIdProdavac_bacaNullPointerException() {
        assertThrows(NullPointerException.class, () -> racunServis.racuniZaProdavca(null));
        verify(racunRepository, never()).findByProdavac_IdProdavac(any());
    }

    @Test
    @DisplayName("racuniZaProdavca - validan idProdavac - vraca racune tog prodavca")
    void racuniZaProdavca_validanIdProdavac_vracaRacune() {
        Racun racun = new Racun(java.time.LocalDate.now(), prodavac, kupac);
        List<Racun> ociekivano = List.of(racun);
        when(racunRepository.findByProdavac_IdProdavac(1L)).thenReturn(ociekivano);

        List<Racun> rezultat = racunServis.racuniZaProdavca(1L);

        assertEquals(ociekivano, rezultat);
        verify(racunRepository, times(1)).findByProdavac_IdProdavac(1L);
    }

    @Test
    @DisplayName("racuniZaKupca - null idKupac - baca NullPointerException")
    void racuniZaKupca_nullIdKupac_bacaNullPointerException() {
        assertThrows(NullPointerException.class, () -> racunServis.racuniZaKupca(null));
        verify(racunRepository, never()).findByKupac_IdKupac(any());
    }

    @Test
    @DisplayName("racuniZaKupca - validan idKupac - vraca racune tog kupca")
    void racuniZaKupca_validanIdKupac_vracaRacune() {
        Racun racun = new Racun(java.time.LocalDate.now(), prodavac, kupac);
        List<Racun> ociekivano = List.of(racun);
        when(racunRepository.findByKupac_IdKupac(2L)).thenReturn(ociekivano);

        List<Racun> rezultat = racunServis.racuniZaKupca(2L);

        assertEquals(ociekivano, rezultat);
        verify(racunRepository, times(1)).findByKupac_IdKupac(2L);
    }
}
