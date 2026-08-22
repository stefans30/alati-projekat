package com.stefansamardzija.alati_projekat.service;

import com.stefansamardzija.alati_projekat.exception.EntityNotFoundException;
import com.stefansamardzija.alati_projekat.exception.NevalidniPodaciException;
import com.stefansamardzija.alati_projekat.model.Telefon;
import com.stefansamardzija.alati_projekat.repository.TelefonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servis za upravljanje telefonima.
 */
@Service
public class TelefonService {

    private final TelefonRepository telefonRepository;

    public TelefonService(TelefonRepository telefonRepository) {
        this.telefonRepository = telefonRepository;
    }

    public List<Telefon> sviTelefoni() {
        return telefonRepository.findAll();
    }

    public Telefon pronadjiPoId(Long id) {
        return telefonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Telefon sa id " + id + " ne postoji"));
    }

    public Telefon dodaj(Telefon telefon) {
        if (telefon.getCena() <= 0) {
            throw new NevalidniPodaciException("Cena telefona mora biti veca od 0");
        }
        if (telefon.getNaziv() == null || telefon.getNaziv().isBlank()) {
            throw new NevalidniPodaciException("Naziv telefona ne sme biti prazan");
        }
        return telefonRepository.save(telefon);
    }

    public Telefon izmeni(Long id, Telefon noviPodaci) {
        Telefon postojeci = pronadjiPoId(id);
        postojeci.setNaziv(noviPodaci.getNaziv());
        postojeci.setCena(noviPodaci.getCena());
        postojeci.setSpecifikacije(noviPodaci.getSpecifikacije());
        return telefonRepository.save(postojeci);
    }

    public void obrisi(Long id) {
        Telefon telefon = pronadjiPoId(id);
        telefonRepository.delete(telefon);
    }
}
