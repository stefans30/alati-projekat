package com.stefansamardzija.alati_projekat.service;

import com.stefansamardzija.alati_projekat.exception.EntityNotFoundException;
import com.stefansamardzija.alati_projekat.exception.NevalidniPodaciException;
import com.stefansamardzija.alati_projekat.model.Kupac;
import com.stefansamardzija.alati_projekat.model.Prodavac;
import com.stefansamardzija.alati_projekat.model.Racun;
import com.stefansamardzija.alati_projekat.model.StavkaRacuna;
import com.stefansamardzija.alati_projekat.model.Telefon;
import com.stefansamardzija.alati_projekat.repository.RacunRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RacunService {

    private final RacunRepository racunRepository;
    private final ProdavacService prodavacService;
    private final KupacService kupacService;
    private final TelefonService telefonService;

    public RacunService(RacunRepository racunRepository,
                        ProdavacService prodavacService,
                        KupacService kupacService,
                        TelefonService telefonService) {
        this.racunRepository = racunRepository;
        this.prodavacService = prodavacService;
        this.kupacService = kupacService;
        this.telefonService = telefonService;
    }

    public List<Racun> sviRacuni() {
        return racunRepository.findAll();
    }

    public Racun pronadjiPoId(Long id) {
        return racunRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Racun sa id " + id + " ne postoji"));
    }

    public Racun zakljuciRacun(Long idProdavac, Long idKupac) {
        Prodavac prodavac = prodavacService.pronadjiPoId(idProdavac);
        Kupac kupac = kupacService.pronadjiPoId(idKupac);

        Racun racun = new Racun(LocalDate.now(), 0.0, prodavac, kupac);
        return racunRepository.save(racun);
    }

    public Racun dodajStavku(Long idRacun, Long idTelefon, int kolicina) {
        if (kolicina <= 0) {
            throw new NevalidniPodaciException("Kolicina mora biti veca od 0");
        }

        Racun racun = pronadjiPoId(idRacun);
        Telefon telefon = telefonService.pronadjiPoId(idTelefon);

        double iznos = telefon.getCena() * kolicina;
        StavkaRacuna stavka = new StavkaRacuna(kolicina, telefon.getCena(), iznos, racun, telefon);
        racun.getStavke().add(stavka);

        racun.setUkupanIznos(racun.getUkupanIznos() + iznos);
        return racunRepository.save(racun);
    }

    public void obrisi(Long id) {
        Racun racun = pronadjiPoId(id);
        racunRepository.delete(racun);
    }
}
