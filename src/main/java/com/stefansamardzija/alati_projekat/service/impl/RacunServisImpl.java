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

/**
 * Implementira RacunServis koristeci RacunRepository za pristup podacima
 * o racunima, kao i ProdavacRepository, KupacRepository i TelefonRepository
 * za proveru i ucitavanje podataka potrebnih za kreiranje racuna.
 *
 * @author Stefan Samardzija
 * @version 1.0
 */
@Service
public class RacunServisImpl implements RacunServis {

    /** Repozitorijum za pristup podacima o racunima. */
    private final RacunRepository racunRepository;
    /** Repozitorijum za pristup podacima o prodavcima. */
    private final ProdavacRepository prodavacRepository;
    /** Repozitorijum za pristup podacima o kupcima. */
    private final KupacRepository kupacRepository;
    /** Repozitorijum za pristup podacima o telefonima. */
    private final TelefonRepository telefonRepository;

    /**
     * Kreira novu instancu servisa sa zadatim repozitorijumima.
     *
     * @param racunRepository Repozitorijum za pristup podacima o racunima.
     * @param prodavacRepository Repozitorijum za pristup podacima o prodavcima.
     * @param kupacRepository Repozitorijum za pristup podacima o kupcima.
     * @param telefonRepository Repozitorijum za pristup podacima o telefonima.
     */
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
