package com.stefansamardzija.alati_projekat.service.impl;

import org.springframework.stereotype.Service;
import com.stefansamardzija.alati_projekat.entity.Mesto;
import com.stefansamardzija.alati_projekat.entity.Sertifikat;
import com.stefansamardzija.alati_projekat.repository.MestoRepository;
import com.stefansamardzija.alati_projekat.repository.SertifikatRepository;
import com.stefansamardzija.alati_projekat.service.SifarnikServis;

import java.util.List;

/**
 * Implementira SifarnikServis koristeci MestoRepository za pristup
 * podacima o mestima i SertifikatRepository za pristup podacima o
 * sertifikatima.
 *
 * @author Stefan Samardzija
 * @version 1.0
 */
@Service
public class SifarnikServisImpl implements SifarnikServis {

    /** Repozitorijum za pristup podacima o mestima. */
    private final MestoRepository mestoRepository;
    /** Repozitorijum za pristup podacima o sertifikatima. */
    private final SertifikatRepository sertifikatRepository;

    /**
     * Kreira novu instancu servisa sa zadatim repozitorijumima.
     *
     * @param mestoRepository Repozitorijum za pristup podacima o mestima.
     * @param sertifikatRepository Repozitorijum za pristup podacima o sertifikatima.
     */
    public SifarnikServisImpl(MestoRepository mestoRepository, SertifikatRepository sertifikatRepository) {
        this.mestoRepository = mestoRepository;
        this.sertifikatRepository = sertifikatRepository;
    }

    @Override
    public List<Mesto> ucitajMesta() {
        return mestoRepository.findAll();
    }

    @Override
    public Mesto kreirajMesto(Mesto mesto) {
        if (mesto == null) throw new NullPointerException("Mesto ne sme biti null");
        return mestoRepository.save(mesto);
    }

    @Override
    public List<Sertifikat> ucitajSertifikate() {
        return sertifikatRepository.findAll();
    }

    @Override
    public Sertifikat kreirajSertifikat(Sertifikat sertifikat) {
        if (sertifikat == null) throw new NullPointerException("Sertifikat ne sme biti null");
        return sertifikatRepository.save(sertifikat);
    }
}
