package com.stefansamardzija.alati_projekat.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.stefansamardzija.alati_projekat.entity.Mesto;
import com.stefansamardzija.alati_projekat.entity.Sertifikat;
import com.stefansamardzija.alati_projekat.repository.MestoRepository;
import com.stefansamardzija.alati_projekat.repository.SertifikatRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SifarnikServisImplTest {

    @Mock
    private MestoRepository mestoRepository;

    @Mock
    private SertifikatRepository sertifikatRepository;

    @InjectMocks
    private SifarnikServisImpl sifarnikServis;

    @Test
    @DisplayName("ucitajMesta - postoje mesta - vraca sva mesta")
    void ucitajMesta_postojeMesta_vracaSvaMesta() {
        List<Mesto> mesta = List.of(new Mesto("Novi Sad"), new Mesto("Beograd"));
        when(mestoRepository.findAll()).thenReturn(mesta);

        List<Mesto> rezultat = sifarnikServis.ucitajMesta();

        assertEquals(mesta, rezultat);
        verify(mestoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("kreirajMesto - null mesto - baca NullPointerException")
    void kreirajMesto_nullMesto_bacaNullPointerException() {
        assertThrows(NullPointerException.class, () -> sifarnikServis.kreirajMesto(null));
        verify(mestoRepository, never()).save(any());
    }

    @Test
    @DisplayName("kreirajMesto - validno mesto - cuva u repozitorijumu")
    void kreirajMesto_validnoMesto_cuvaMesto() {
        Mesto mesto = new Mesto("Novi Sad");
        when(mestoRepository.save(mesto)).thenReturn(mesto);

        Mesto rezultat = sifarnikServis.kreirajMesto(mesto);

        assertEquals(mesto, rezultat);
        verify(mestoRepository, times(1)).save(mesto);
    }

    @Test
    @DisplayName("ucitajSertifikate - postoje sertifikati - vraca sve sertifikate")
    void ucitajSertifikate_postojeSertifikati_vracaSveSertifikate() {
        List<Sertifikat> sertifikati = List.of(new Sertifikat("Junior"), new Sertifikat("Senior"));
        when(sertifikatRepository.findAll()).thenReturn(sertifikati);

        List<Sertifikat> rezultat = sifarnikServis.ucitajSertifikate();

        assertEquals(sertifikati, rezultat);
        verify(sertifikatRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("kreirajSertifikat - null sertifikat - baca NullPointerException")
    void kreirajSertifikat_nullSertifikat_bacaNullPointerException() {
        assertThrows(NullPointerException.class, () -> sifarnikServis.kreirajSertifikat(null));
        verify(sertifikatRepository, never()).save(any());
    }

    @Test
    @DisplayName("kreirajSertifikat - validan sertifikat - cuva u repozitorijumu")
    void kreirajSertifikat_validanSertifikat_cuvaSertifikat() {
        Sertifikat sertifikat = new Sertifikat("Senior");
        when(sertifikatRepository.save(sertifikat)).thenReturn(sertifikat);

        Sertifikat rezultat = sifarnikServis.kreirajSertifikat(sertifikat);

        assertEquals(sertifikat, rezultat);
        verify(sertifikatRepository, times(1)).save(sertifikat);
    }
}
