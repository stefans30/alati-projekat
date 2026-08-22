package com.stefansamardzija.alati_projekat.service;

import com.stefansamardzija.alati_projekat.exception.EntityNotFoundException;
import com.stefansamardzija.alati_projekat.exception.NevalidniPodaciException;
import com.stefansamardzija.alati_projekat.model.Prodavac;
import com.stefansamardzija.alati_projekat.repository.ProdavacRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdavacService {

    private final ProdavacRepository prodavacRepository;

    public ProdavacService(ProdavacRepository prodavacRepository) {
        this.prodavacRepository = prodavacRepository;
    }

    public List<Prodavac> sviProdavci() {
        return prodavacRepository.findAll();
    }

    public Prodavac pronadjiPoId(Long id) {
        return prodavacRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Prodavac sa id " + id + " ne postoji"));
    }

    public Prodavac dodaj(Prodavac prodavac) {
        if (prodavac.getKorisnickoIme() == null || prodavac.getKorisnickoIme().isBlank()) {
            throw new NevalidniPodaciException("Korisnicko ime ne sme biti prazno");
        }
        if (prodavac.getSifra() == null || prodavac.getSifra().length() < 6) {
            throw new NevalidniPodaciException("Sifra mora imati bar 6 karaktera");
        }
        return prodavacRepository.save(prodavac);
    }

    public Prodavac izmeni(Long id, Prodavac noviPodaci) {
        Prodavac postojeci = pronadjiPoId(id);
        postojeci.setIme(noviPodaci.getIme());
        postojeci.setPrezime(noviPodaci.getPrezime());
        postojeci.setMejl(noviPodaci.getMejl());
        return prodavacRepository.save(postojeci);
    }

    public void obrisi(Long id) {
        Prodavac prodavac = pronadjiPoId(id);
        prodavacRepository.delete(prodavac);
    }
}
