package com.stefansamardzija.alati_projekat.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.stefansamardzija.alati_projekat.dto.KreirajRacunZahtev;
import com.stefansamardzija.alati_projekat.dto.StavkaZahtev;
import com.stefansamardzija.alati_projekat.entity.*;
import com.stefansamardzija.alati_projekat.repository.KupacRepository;
import com.stefansamardzija.alati_projekat.repository.TelefonRepository;
import com.stefansamardzija.alati_projekat.repository.ProdavacRepository;
import com.stefansamardzija.alati_projekat.repository.RacunRepository;
import com.stefansamardzija.alati_projekat.service.RacunServis;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RacunServisImpl implements RacunServis {

    private final RacunRepository racunRepository;
    private final ProdavacRepository prodavacRepository;
    private final KupacRepository kupacRepository;
    private final TelefonRepository telefonRepository;

    public RacunServisImpl(RacunRepository racunRepository, ProdavacRepository prodavacRepository,
                           KupacRepository kupacRepository, TelefonRepository telefonRepository) {
        this.racunRepository = racunRepository;
        this.prodavacRepository = prodavacRepository;
        this.kupacRepository = kupacRepository;
        this.telefonRepository = telefonRepository;
    }

    @Override
    @Transactional
    public Racun kreirajRacun(KreirajRacunZahtev zahtev) {
        if (zahtev == null) throw new NullPointerException("Zahtev ne sme biti null");
        Prodavac prodavac = prodavacRepository.findById(zahtev.idProdavac())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prodavac ne postoji"));
        Kupac kupac = kupacRepository.findById(zahtev.idKupac())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kupac ne postoji"));

        if (zahtev.stavke() == null || zahtev.stavke().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Racun mora imati bar jednu stavku");
        }

        Racun racun = new Racun(LocalDate.now(), prodavac, kupac);

        for (StavkaZahtev sz : zahtev.stavke()) {
            Telefon telefon = telefonRepository.findById(sz.idTelefon())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Telefon ne postoji: " + sz.idTelefon()));
            BigDecimal cena = sz.cena() != null ? sz.cena() : telefon.getCena();
            StavkaRacuna stavka = new StavkaRacuna(cena, sz.kolicina(), telefon);
            racun.dodajStavku(stavka);
        }
        racun.preracunajUkupanIznos();
        return racunRepository.save(racun);
    }

    @Override
    public List<Racun> pretraziRacune() {
        return racunRepository.findAll();
    }

    @Override
    public Racun vratiRacun(Long id) {
        if (id == null) throw new NullPointerException("ID ne sme biti null");
        return racunRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Racun ne postoji: " + id));
    }

    @Override
    public List<Racun> racuniZaProdavca(Long idProdavac) {
        if (idProdavac == null) throw new NullPointerException("ID prodavca ne sme biti null");
        return racunRepository.findByProdavac_IdProdavac(idProdavac);
    }

    @Override
    public List<Racun> racuniZaKupca(Long idKupac) {
        if (idKupac == null) throw new NullPointerException("ID kupca ne sme biti null");
        return racunRepository.findByKupac_IdKupac(idKupac);
    }
}
