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

import com.stefansamardzija.alati_projekat.entity.FizickoLice;
import com.stefansamardzija.alati_projekat.entity.Kupac;
import com.stefansamardzija.alati_projekat.entity.Mesto;
import com.stefansamardzija.alati_projekat.entity.PravnoLice;
import com.stefansamardzija.alati_projekat.repository.KupacRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KupacServisImplTest {

    @Mock
    private KupacRepository kupacRepository;

    @InjectMocks
    private KupacServisImpl kupacServis;

    private Mesto mesto;
    private FizickoLice fizickoLice;

    @BeforeEach
    void setUp() {
        mesto = new Mesto("Novi Sad");
        fizickoLice = new FizickoLice("pera@mail.com", mesto, "Pera", "Peric", "1234567890123");
    }

    @Test
    @DisplayName("kreirajKupca - null kupac - baca NullPointerException")
    void kreirajKupca_nullKupac_bacaNullPointerException() {
        assertThrows(NullPointerException.class, () -> kupacServis.kreirajKupca(null));
        verify(kupacRepository, never()).save(any());
    }

    @Test
    @DisplayName("kreirajKupca - validan kupac - resetuje id i cuva u repozitorijumu")
    void kreirajKupca_validanKupac_cuvaKupca() {
        fizickoLice.setIdKupac(5L);
        when(kupacRepository.save(any(Kupac.class))).thenAnswer(inv -> inv.getArgument(0));

        Kupac rezultat = kupacServis.kreirajKupca(fizickoLice);

        ArgumentCaptor<Kupac> captor = ArgumentCaptor.forClass(Kupac.class);
        verify(kupacRepository, times(1)).save(captor.capture());
        assertNull(captor.getValue().getIdKupac(), "id treba biti resetovan na null pre cuvanja");
        assertEquals(fizickoLice, rezultat);
    }

    @Test
    @DisplayName("izmeniKupca - null id - baca NullPointerException")
    void izmeniKupca_nullId_bacaNullPointerException() {
        assertThrows(NullPointerException.class, () -> kupacServis.izmeniKupca(null, fizickoLice));
        verify(kupacRepository, never()).findById(any());
        verify(kupacRepository, never()).save(any());
    }

    @Test
    @DisplayName("izmeniKupca - null izmene - baca NullPointerException")
    void izmeniKupca_nullIzmene_bacaNullPointerException() {
        assertThrows(NullPointerException.class, () -> kupacServis.izmeniKupca(1L, null));
        verify(kupacRepository, never()).findById(any());
        verify(kupacRepository, never()).save(any());
    }

    @Test
    @DisplayName("izmeniKupca - kupac ne postoji - baca ResponseStatusException NOT_FOUND")
    void izmeniKupca_kupacNePostoji_bacaNotFound() {
        when(kupacRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> kupacServis.izmeniKupca(1L, fizickoLice));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(kupacRepository, never()).save(any());
    }

    @Test
    @DisplayName("izmeniKupca - razlicit tip kupca - baca ResponseStatusException BAD_REQUEST")
    void izmeniKupca_razlicitTip_bacaBadRequest() {
        FizickoLice postojeci = new FizickoLice("stari@mail.com", mesto, "Stari", "Staric", "1111111111111");
        postojeci.setIdKupac(1L);
        PravnoLice izmenePravno = new PravnoLice("firma@mail.com", mesto, "Firma DOO", "123456789");

        when(kupacRepository.findById(1L)).thenReturn(Optional.of(postojeci));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> kupacServis.izmeniKupca(1L, izmenePravno));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        verify(kupacRepository, never()).save(any());
    }

    @Test
    @DisplayName("izmeniKupca - fizicko lice - azurira zajednicka i specificna polja i cuva")
    void izmeniKupca_fizickoLice_azuriraICuva() {
        FizickoLice postojeci = new FizickoLice("stari@mail.com", mesto, "Stari", "Staric", "1111111111111");
        postojeci.setIdKupac(1L);
        Mesto novoMesto = new Mesto("Beograd");
        FizickoLice izmene = new FizickoLice("novi@mail.com", novoMesto, "Novi", "Novic", "2222222222222");

        when(kupacRepository.findById(1L)).thenReturn(Optional.of(postojeci));
        when(kupacRepository.save(any(Kupac.class))).thenAnswer(inv -> inv.getArgument(0));

        Kupac rezultat = kupacServis.izmeniKupca(1L, izmene);

        ArgumentCaptor<Kupac> captor = ArgumentCaptor.forClass(Kupac.class);
        verify(kupacRepository, times(1)).save(captor.capture());
        FizickoLice sacuvan = (FizickoLice) captor.getValue();
        assertEquals("novi@mail.com", sacuvan.getMejl());
        assertEquals(novoMesto, sacuvan.getMesto());
        assertEquals("Novi", sacuvan.getIme());
        assertEquals("Novic", sacuvan.getPrezime());
        assertEquals("2222222222222", sacuvan.getJmbg());
        assertSame(postojeci, rezultat);
    }

    @Test
    @DisplayName("izmeniKupca - pravno lice - azurira zajednicka i specificna polja i cuva")
    void izmeniKupca_pravnoLice_azuriraICuva() {
        PravnoLice postojeci = new PravnoLice("stari@mail.com", mesto, "Stara Firma", "111111111");
        postojeci.setIdKupac(1L);
        Mesto novoMesto = new Mesto("Beograd");
        PravnoLice izmene = new PravnoLice("novi@mail.com", novoMesto, "Nova Firma", "222222222");

        when(kupacRepository.findById(1L)).thenReturn(Optional.of(postojeci));
        when(kupacRepository.save(any(Kupac.class))).thenAnswer(inv -> inv.getArgument(0));

        Kupac rezultat = kupacServis.izmeniKupca(1L, izmene);

        ArgumentCaptor<Kupac> captor = ArgumentCaptor.forClass(Kupac.class);
        verify(kupacRepository, times(1)).save(captor.capture());
        PravnoLice sacuvan = (PravnoLice) captor.getValue();
        assertEquals("novi@mail.com", sacuvan.getMejl());
        assertEquals(novoMesto, sacuvan.getMesto());
        assertEquals("Nova Firma", sacuvan.getNazivFirme());
        assertEquals("222222222", sacuvan.getPib());
        assertSame(postojeci, rezultat);
    }

    @Test
    @DisplayName("obrisiKupca - null id - baca NullPointerException")
    void obrisiKupca_nullId_bacaNullPointerException() {
        assertThrows(NullPointerException.class, () -> kupacServis.obrisiKupca(null));
        verify(kupacRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("obrisiKupca - kupac ne postoji - baca ResponseStatusException NOT_FOUND")
    void obrisiKupca_kupacNePostoji_bacaNotFound() {
        when(kupacRepository.existsById(1L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> kupacServis.obrisiKupca(1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(kupacRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("obrisiKupca - kupac postoji - brise kupca")
    void obrisiKupca_kupacPostoji_briseKupca() {
        when(kupacRepository.existsById(1L)).thenReturn(true);

        kupacServis.obrisiKupca(1L);

        verify(kupacRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("pretraziKupce - postoje kupci - vraca sve kupce")
    void pretraziKupce_postojeKupci_vracaSveKupce() {
        List<Kupac> svi = List.of(fizickoLice);
        when(kupacRepository.findAll()).thenReturn(svi);

        List<Kupac> rezultat = kupacServis.pretraziKupce();

        assertEquals(svi, rezultat);
        verify(kupacRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("vratiKupca - null id - baca NullPointerException")
    void vratiKupca_nullId_bacaNullPointerException() {
        assertThrows(NullPointerException.class, () -> kupacServis.vratiKupca(null));
        verify(kupacRepository, never()).findById(any());
    }

    @Test
    @DisplayName("vratiKupca - kupac ne postoji - baca ResponseStatusException NOT_FOUND")
    void vratiKupca_kupacNePostoji_bacaNotFound() {
        when(kupacRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> kupacServis.vratiKupca(1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    @DisplayName("vratiKupca - kupac postoji - vraca kupca")
    void vratiKupca_kupacPostoji_vracaKupca() {
        fizickoLice.setIdKupac(1L);
        when(kupacRepository.findById(1L)).thenReturn(Optional.of(fizickoLice));

        Kupac rezultat = kupacServis.vratiKupca(1L);

        assertEquals(fizickoLice, rezultat);
        verify(kupacRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("kupciPoMestu - null idMesto - baca NullPointerException")
    void kupciPoMestu_nullIdMesto_bacaNullPointerException() {
        assertThrows(NullPointerException.class, () -> kupacServis.kupciPoMestu(null));
        verify(kupacRepository, never()).findByMesto_IdMesto(any());
    }

    @Test
    @DisplayName("kupciPoMestu - validan idMesto - vraca kupce iz tog mesta")
    void kupciPoMestu_validanIdMesto_vracaKupce() {
        List<Kupac> ociekivano = List.of(fizickoLice);
        when(kupacRepository.findByMesto_IdMesto(1L)).thenReturn(ociekivano);

        List<Kupac> rezultat = kupacServis.kupciPoMestu(1L);

        assertEquals(ociekivano, rezultat);
        verify(kupacRepository, times(1)).findByMesto_IdMesto(1L);
    }

    @Test
    @DisplayName("kupciPoTipu - null tip - baca NullPointerException")
    void kupciPoTipu_nullTip_bacaNullPointerException() {
        assertThrows(NullPointerException.class, () -> kupacServis.kupciPoTipu(null));
        verify(kupacRepository, never()).findByTipKupca(any());
    }

    @Test
    @DisplayName("kupciPoTipu - validan tip - vraca kupce tog tipa")
    void kupciPoTipu_validanTip_vracaKupce() {
        List<Kupac> ociekivano = List.of(fizickoLice);
        when(kupacRepository.findByTipKupca("fizicko")).thenReturn(ociekivano);

        List<Kupac> rezultat = kupacServis.kupciPoTipu("fizicko");

        assertEquals(ociekivano, rezultat);
        verify(kupacRepository, times(1)).findByTipKupca("fizicko");
    }
}
