package com.stefansamardzija.alati_projekat.service;

import com.stefansamardzija.alati_projekat.exception.EntityNotFoundException;
import com.stefansamardzija.alati_projekat.exception.NevalidniPodaciException;
import com.stefansamardzija.alati_projekat.model.Mesto;
import com.stefansamardzija.alati_projekat.repository.MestoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MestoService {

    private final MestoRepository mestoRepository;

    public MestoService(MestoRepository mestoRepository) {
        this.mestoRepository = mestoRepository;
    }

    public List<Mesto> svaMesta() {
        return mestoRepository.findAll();
    }

    public Mesto pronadjiPoId(Long id) {
        return mestoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mesto sa id " + id + " ne postoji"));
    }

    public Mesto dodaj(Mesto mesto) {
        if (mesto.getNaziv() == null || mesto.getNaziv().isBlank()) {
            throw new NevalidniPodaciException("Naziv mesta ne sme biti prazan");
        }
        return mestoRepository.save(mesto);
    }

    public Mesto izmeni(Long id, Mesto noviPodaci) {
        Mesto postojece = pronadjiPoId(id);
        postojece.setNaziv(noviPodaci.getNaziv());
        return mestoRepository.save(postojece);
    }

    public void obrisi(Long id) {
        Mesto mesto = pronadjiPoId(id);
        mestoRepository.delete(mesto);
    }
}