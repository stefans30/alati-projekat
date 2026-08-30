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

import com.stefansamardzija.alati_projekat.dto.DodeliSertifikatZahtev;
import com.stefansamardzija.alati_projekat.entity.Prodavac;
import com.stefansamardzija.alati_projekat.entity.ProdavacSertifikat;
import com.stefansamardzija.alati_projekat.entity.Sertifikat;
import com.stefansamardzija.alati_projekat.repository.ProdavacRepository;
import com.stefansamardzija.alati_projekat.repository.ProdavacSertifikatRepository;
import com.stefansamardzija.alati_projekat.repository.SertifikatRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdavacServisImplTest {

    @Mock
    private ProdavacRepository prodavacRepository;

    @Mock
    private SertifikatRepository sertifikatRepository;

    @Mock
    private ProdavacSertifikatRepository prodavacSertifikatRepository;

    @InjectMocks
    private ProdavacServisImpl prodavacServis;

    private Prodavac prodavac;

    @BeforeEach
    void setUp() {
        prodavac = new Prodavac("Pera", "Peric", "pera@mail.com", "pperic", "tajna123");
    }

    @Test
    @DisplayName("prijaviProdavca - null korisnicko ime - baca NullPointerException")
    void prijaviProdavca_nullKorisnickoIme_bacaNullPointerException() {
        assertThrows(NullPointerException.class, () -> prodavacServis.prijaviProdavca(null, "tajna123"));
        verify(prodavacRepository, never()).findByKorisnickoIme(any());
    }

    @Test
    @DisplayName("prijaviProdavca - null sifra - baca NullPointerException")
    void prijaviProdavca_nullSifra_bacaNullPointerException() {
        assertThrows(NullPointerException.class, () -> prodavacServis.prijaviProdavca("pperic", null));
        verify(prodavacRepository, never()).findByKorisnickoIme(any());
    }

    @Test
    @DisplayName("prijaviProdavca - korisnicko ime ne postoji - baca ResponseStatusException UNAUTHORIZED")
    void prijaviProdavca_korisnickoImeNePostoji_bacaUnauthorized() {
        when(prodavacRepository.findByKorisnickoIme("nepostojeci")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> prodavacServis.prijaviProdavca("nepostojeci", "tajna123"));

        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
    }

    @Test
    @DisplayName("prijaviProdavca - pogresna sifra - baca ResponseStatusException UNAUTHORIZED")
    void prijaviProdavca_pogresnaSifra_bacaUnauthorized() {
        when(prodavacRepository.findByKorisnickoIme("pperic")).thenReturn(Optional.of(prodavac));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> prodavacServis.prijaviProdavca("pperic", "pogresna"));

        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
    }

    @Test
    @DisplayName("prijaviProdavca - ispravni podaci - vraca prodavca")
    void prijaviProdavca_ispravniPodaci_vracaProdavca() {
        when(prodavacRepository.findByKorisnickoIme("pperic")).thenReturn(Optional.of(prodavac));

        Prodavac rezultat = prodavacServis.prijaviProdavca("pperic", "tajna123");

        assertEquals(prodavac, rezultat);
        verify(prodavacRepository, times(1)).findByKorisnickoIme("pperic");
    }

    @Test
    @DisplayName("sacuvajProdavca - null prodavac - baca NullPointerException")
    void sacuvajProdavca_nullProdavac_bacaNullPointerException() {
        assertThrows(NullPointerException.class, () -> prodavacServis.sacuvajProdavca(null));
        verify(prodavacRepository, never()).save(any());
    }

    @Test
    @DisplayName("sacuvajProdavca - validan prodavac - cuva u repozitorijumu")
    void sacuvajProdavca_validanProdavac_cuvaProdavca() {
        when(prodavacRepository.save(prodavac)).thenReturn(prodavac);

        Prodavac rezultat = prodavacServis.sacuvajProdavca(prodavac);

        assertEquals(prodavac, rezultat);
        verify(prodavacRepository, times(1)).save(prodavac);
    }

    @Test
    @DisplayName("pretraziProdavce - null pretraga - vraca sve prodavce")
    void pretraziProdavce_nullPretraga_vracaSveProdavce() {
        List<Prodavac> svi = List.of(prodavac);
        when(prodavacRepository.findAll()).thenReturn(svi);

        List<Prodavac> rezultat = prodavacServis.pretraziProdavce(null);

        assertEquals(svi, rezultat);
        verify(prodavacRepository, times(1)).findAll();
        verify(prodavacRepository, never())
                .findByImeContainingIgnoreCaseOrPrezimeContainingIgnoreCase(any(), any());
    }

    @Test
    @DisplayName("pretraziProdavce - prazna pretraga - vraca sve prodavce")
    void pretraziProdavce_praznaPretraga_vracaSveProdavce() {
        List<Prodavac> svi = List.of(prodavac);
        when(prodavacRepository.findAll()).thenReturn(svi);

        List<Prodavac> rezultat = prodavacServis.pretraziProdavce("   ");

        assertEquals(svi, rezultat);
        verify(prodavacRepository, times(1)).findAll();
        verify(prodavacRepository, never())
                .findByImeContainingIgnoreCaseOrPrezimeContainingIgnoreCase(any(), any());
    }

    @Test
    @DisplayName("pretraziProdavce - zadata pretraga - vraca filtrirane prodavce")
    void pretraziProdavce_zadataPretraga_vracaFiltrirane() {
        List<Prodavac> filtrirani = List.of(prodavac);
        when(prodavacRepository.findByImeContainingIgnoreCaseOrPrezimeContainingIgnoreCase("Pera", "Pera"))
                .thenReturn(filtrirani);

        List<Prodavac> rezultat = prodavacServis.pretraziProdavce("Pera");

        assertEquals(filtrirani, rezultat);
        verify(prodavacRepository, times(1))
                .findByImeContainingIgnoreCaseOrPrezimeContainingIgnoreCase("Pera", "Pera");
        verify(prodavacRepository, never()).findAll();
    }

    @Test
    @DisplayName("dodeliSertifikat - null idProdavac - baca NullPointerException")
    void dodeliSertifikat_nullIdProdavac_bacaNullPointerException() {
        DodeliSertifikatZahtev zahtev = new DodeliSertifikatZahtev(1L, LocalDate.now());
        assertThrows(NullPointerException.class, () -> prodavacServis.dodeliSertifikat(null, zahtev));
        verify(prodavacRepository, never()).findById(any());
        verify(prodavacSertifikatRepository, never()).save(any());
    }

    @Test
    @DisplayName("dodeliSertifikat - null zahtev - baca NullPointerException")
    void dodeliSertifikat_nullZahtev_bacaNullPointerException() {
        assertThrows(NullPointerException.class, () -> prodavacServis.dodeliSertifikat(1L, null));
        verify(prodavacRepository, never()).findById(any());
        verify(prodavacSertifikatRepository, never()).save(any());
    }

    @Test
    @DisplayName("dodeliSertifikat - prodavac ne postoji - baca ResponseStatusException NOT_FOUND")
    void dodeliSertifikat_prodavacNePostoji_bacaNotFound() {
        DodeliSertifikatZahtev zahtev = new DodeliSertifikatZahtev(1L, LocalDate.now());
        when(prodavacRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> prodavacServis.dodeliSertifikat(1L, zahtev));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(sertifikatRepository, never()).findById(any());
        verify(prodavacSertifikatRepository, never()).save(any());
    }

    @Test
    @DisplayName("dodeliSertifikat - sertifikat ne postoji - baca ResponseStatusException NOT_FOUND")
    void dodeliSertifikat_sertifikatNePostoji_bacaNotFound() {
        DodeliSertifikatZahtev zahtev = new DodeliSertifikatZahtev(1L, LocalDate.now());
        prodavac.setIdProdavac(1L);
        when(prodavacRepository.findById(1L)).thenReturn(Optional.of(prodavac));
        when(sertifikatRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> prodavacServis.dodeliSertifikat(1L, zahtev));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(prodavacSertifikatRepository, never()).save(any());
    }

    @Test
    @DisplayName("dodeliSertifikat - validan zahtev - kreira i cuva zapis o sertifikatu")
    void dodeliSertifikat_validanZahtev_kreiraICuvaZapis() {
        LocalDate datum = LocalDate.of(2026, 1, 15);
        DodeliSertifikatZahtev zahtev = new DodeliSertifikatZahtev(2L, datum);
        prodavac.setIdProdavac(1L);
        Sertifikat sertifikat = new Sertifikat("Senior");
        sertifikat.setIdSertifikat(2L);

        when(prodavacRepository.findById(1L)).thenReturn(Optional.of(prodavac));
        when(sertifikatRepository.findById(2L)).thenReturn(Optional.of(sertifikat));
        when(prodavacSertifikatRepository.save(any(ProdavacSertifikat.class))).thenAnswer(inv -> inv.getArgument(0));

        ProdavacSertifikat rezultat = prodavacServis.dodeliSertifikat(1L, zahtev);

        ArgumentCaptor<ProdavacSertifikat> captor = ArgumentCaptor.forClass(ProdavacSertifikat.class);
        verify(prodavacSertifikatRepository, times(1)).save(captor.capture());
        ProdavacSertifikat sacuvan = captor.getValue();
        assertEquals(prodavac, sacuvan.getProdavac());
        assertEquals(sertifikat, sacuvan.getSertifikat());
        assertEquals(datum, sacuvan.getDatumIzdavanja());
        assertSame(sacuvan, rezultat);
    }
}
