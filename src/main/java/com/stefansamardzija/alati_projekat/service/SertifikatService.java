package com.stefansamardzija.alati_projekat.service;

import com.stefansamardzija.alati_projekat.exception.EntityNotFoundException;
import com.stefansamardzija.alati_projekat.exception.NevalidniPodaciException;
import com.stefansamardzija.alati_projekat.model.Sertifikat;
import com.stefansamardzija.alati_projekat.repository.SertifikatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SertifikatService {

    private final SertifikatRepository sertifikatRepository;

    public SertifikatService(SertifikatRepository sertifikatRepository) {
        this.sertifikatRepository = sertifikatRepository;
    }

    public List<Sertifikat> sviSertifikati() {
        return sertifikatRepository.findAll();
    }

    public Sertifikat pronadjiPoId(Long id) {
        return sertifikatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sertifikat sa id " + id + " ne postoji"));
    }

    public Sertifikat dodaj(Sertifikat sertifikat) {
        if (sertifikat.getZvanje() == null || sertifikat.getZvanje().isBlank()) {
            throw new NevalidniPodaciException("Zvanje sertifikata ne sme biti prazno");
        }
        return sertifikatRepository.save(sertifikat);
    }

    public void obrisi(Long id) {
        Sertifikat sertifikat = pronadjiPoId(id);
        sertifikatRepository.delete(sertifikat);
    }
}
