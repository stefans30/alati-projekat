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

import com.stefansamardzija.alati_projekat.entity.Telefon;
import com.stefansamardzija.alati_projekat.repository.TelefonRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TelefonServisImplTest {

    @Mock
    private TelefonRepository telefonRepository;

    @InjectMocks
    private TelefonServisImpl telefonServis;

    private Telefon telefon;

    @BeforeEach
    void setUp() {
        telefon = new Telefon("iPhone 15", new BigDecimal("999.99"), "128GB, crna boja");
    }

    @Test
    @DisplayName("kreirajTelefon - null telefon - baca NullPointerException")
    void kreirajTelefon_nullTelefon_bacaNullPointerException() {
        assertThrows(NullPointerException.class, () -> telefonServis.kreirajTelefon(null));
        verify(telefonRepository, never()).save(any());
    }

    @Test
    @DisplayName("kreirajTelefon - validan telefon - resetuje id i cuva u repozitorijumu")
    void kreirajTelefon_validanTelefon_cuvaTelefon() {
        telefon.setIdTelefon(5L);
        when(telefonRepository.save(any(Telefon.class))).thenAnswer(inv -> inv.getArgument(0));

        Telefon rezultat = telefonServis.kreirajTelefon(telefon);

        ArgumentCaptor<Telefon> captor = ArgumentCaptor.forClass(Telefon.class);
        verify(telefonRepository, times(1)).save(captor.capture());
        assertNull(captor.getValue().getIdTelefon(), "id treba biti resetovan na null pre cuvanja");
        assertEquals(telefon, rezultat);
    }

    @Test
    @DisplayName("izmeniTelefon - null id - baca NullPointerException")
    void izmeniTelefon_nullId_bacaNullPointerException() {
        assertThrows(NullPointerException.class, () -> telefonServis.izmeniTelefon(null, telefon));
        verify(telefonRepository, never()).findById(any());
        verify(telefonRepository, never()).save(any());
    }

    @Test
    @DisplayName("izmeniTelefon - null izmene - baca NullPointerException")
    void izmeniTelefon_nullIzmene_bacaNullPointerException() {
        assertThrows(NullPointerException.class, () -> telefonServis.izmeniTelefon(1L, null));
        verify(telefonRepository, never()).findById(any());
        verify(telefonRepository, never()).save(any());
    }

    @Test
    @DisplayName("izmeniTelefon - telefon ne postoji - baca ResponseStatusException NOT_FOUND")
    void izmeniTelefon_telefonNePostoji_bacaNotFound() {
        when(telefonRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> telefonServis.izmeniTelefon(1L, telefon));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(telefonRepository, never()).save(any());
    }

    @Test
    @DisplayName("izmeniTelefon - postojeci telefon - azurira polja i cuva")
    void izmeniTelefon_postojeciTelefon_azuriraICuva() {
        Telefon postojeci = new Telefon("Stari", new BigDecimal("100.00"), "stare spec");
        postojeci.setIdTelefon(1L);
        Telefon izmene = new Telefon("Novi", new BigDecimal("200.00"), "nove spec");

        when(telefonRepository.findById(1L)).thenReturn(Optional.of(postojeci));
        when(telefonRepository.save(any(Telefon.class))).thenAnswer(inv -> inv.getArgument(0));

        Telefon rezultat = telefonServis.izmeniTelefon(1L, izmene);

        ArgumentCaptor<Telefon> captor = ArgumentCaptor.forClass(Telefon.class);
        verify(telefonRepository, times(1)).save(captor.capture());
        Telefon sacuvan = captor.getValue();
        assertEquals("Novi", sacuvan.getNaziv());
        assertEquals(new BigDecimal("200.00"), sacuvan.getCena());
        assertEquals("nove spec", sacuvan.getSpecifikacije());
        assertSame(postojeci, rezultat);
    }

    @Test
    @DisplayName("obrisiTelefon - null id - baca NullPointerException")
    void obrisiTelefon_nullId_bacaNullPointerException() {
        assertThrows(NullPointerException.class, () -> telefonServis.obrisiTelefon(null));
        verify(telefonRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("obrisiTelefon - telefon ne postoji - baca ResponseStatusException NOT_FOUND")
    void obrisiTelefon_telefonNePostoji_bacaNotFound() {
        when(telefonRepository.existsById(1L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> telefonServis.obrisiTelefon(1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(telefonRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("obrisiTelefon - telefon postoji - brise telefon")
    void obrisiTelefon_telefonPostoji_briseTelefon() {
        when(telefonRepository.existsById(1L)).thenReturn(true);

        telefonServis.obrisiTelefon(1L);

        verify(telefonRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("pretraziTelefone - naziv je null - vraca sve telefone")
    void pretraziTelefone_nazivNull_vracaSveTelefone() {
        List<Telefon> svi = List.of(telefon);
        when(telefonRepository.findAll()).thenReturn(svi);

        List<Telefon> rezultat = telefonServis.pretraziTelefone(null);

        assertEquals(svi, rezultat);
        verify(telefonRepository, times(1)).findAll();
        verify(telefonRepository, never()).findByNazivContainingIgnoreCase(any());
    }

    @Test
    @DisplayName("pretraziTelefone - naziv je prazan string - vraca sve telefone")
    void pretraziTelefone_nazivPrazan_vracaSveTelefone() {
        List<Telefon> svi = List.of(telefon);
        when(telefonRepository.findAll()).thenReturn(svi);

        List<Telefon> rezultat = telefonServis.pretraziTelefone("   ");

        assertEquals(svi, rezultat);
        verify(telefonRepository, times(1)).findAll();
        verify(telefonRepository, never()).findByNazivContainingIgnoreCase(any());
    }

    @Test
    @DisplayName("pretraziTelefone - naziv je zadat - vraca filtrirane telefone")
    void pretraziTelefone_nazivZadat_vracaFiltrirane() {
        List<Telefon> filtrirani = List.of(telefon);
        when(telefonRepository.findByNazivContainingIgnoreCase("iPhone")).thenReturn(filtrirani);

        List<Telefon> rezultat = telefonServis.pretraziTelefone("iPhone");

        assertEquals(filtrirani, rezultat);
        verify(telefonRepository, times(1)).findByNazivContainingIgnoreCase("iPhone");
        verify(telefonRepository, never()).findAll();
    }

    @Test
    @DisplayName("vratiTelefon - null id - baca NullPointerException")
    void vratiTelefon_nullId_bacaNullPointerException() {
        assertThrows(NullPointerException.class, () -> telefonServis.vratiTelefon(null));
        verify(telefonRepository, never()).findById(any());
    }

    @Test
    @DisplayName("vratiTelefon - telefon ne postoji - baca ResponseStatusException NOT_FOUND")
    void vratiTelefon_telefonNePostoji_bacaNotFound() {
        when(telefonRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> telefonServis.vratiTelefon(1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    @DisplayName("vratiTelefon - telefon postoji - vraca telefon")
    void vratiTelefon_telefonPostoji_vracaTelefon() {
        telefon.setIdTelefon(1L);
        when(telefonRepository.findById(1L)).thenReturn(Optional.of(telefon));

        Telefon rezultat = telefonServis.vratiTelefon(1L);

        assertEquals(telefon, rezultat);
        verify(telefonRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("telefoniPoRasponuCene - min je null - baca NullPointerException")
    void telefoniPoRasponuCene_minNull_bacaNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> telefonServis.telefoniPoRasponuCene(null, new BigDecimal("100")));
        verify(telefonRepository, never()).findByCenaBetween(any(), any());
    }

    @Test
    @DisplayName("telefoniPoRasponuCene - max je null - baca NullPointerException")
    void telefoniPoRasponuCene_maxNull_bacaNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> telefonServis.telefoniPoRasponuCene(new BigDecimal("10"), null));
        verify(telefonRepository, never()).findByCenaBetween(any(), any());
    }

    @Test
    @DisplayName("telefoniPoRasponuCene - validan opseg - vraca telefone iz opsega")
    void telefoniPoRasponuCene_validanOpseg_vracaTelefone() {
        BigDecimal min = new BigDecimal("100");
        BigDecimal max = new BigDecimal("2000");
        List<Telefon> ociekivano = List.of(telefon);
        when(telefonRepository.findByCenaBetween(min, max)).thenReturn(ociekivano);

        List<Telefon> rezultat = telefonServis.telefoniPoRasponuCene(min, max);

        assertEquals(ociekivano, rezultat);
        verify(telefonRepository, times(1)).findByCenaBetween(min, max);
    }
}
