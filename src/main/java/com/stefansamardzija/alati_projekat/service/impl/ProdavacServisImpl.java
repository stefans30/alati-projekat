package com.stefansamardzija.alati_projekat.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.stefansamardzija.alati_projekat.dto.DodeliSertifikatZahtev;
import com.stefansamardzija.alati_projekat.entity.Prodavac;
import com.stefansamardzija.alati_projekat.entity.ProdavacSertifikat;
import com.stefansamardzija.alati_projekat.entity.Sertifikat;
import com.stefansamardzija.alati_projekat.repository.ProdavacRepository;
import com.stefansamardzija.alati_projekat.repository.ProdavacSertifikatRepository;
import com.stefansamardzija.alati_projekat.repository.SertifikatRepository;
import com.stefansamardzija.alati_projekat.service.ProdavacServis;

import java.util.List;

@Service
public class ProdavacServisImpl implements ProdavacServis {

    private final ProdavacRepository prodavacRepository;
    private final SertifikatRepository sertifikatRepository;
    private final ProdavacSertifikatRepository prodavacSertifikatRepository;

    public ProdavacServisImpl(ProdavacRepository prodavacRepository,
                              SertifikatRepository sertifikatRepository,
                              ProdavacSertifikatRepository prodavacSertifikatRepository) {
        this.prodavacRepository = prodavacRepository;
        this.sertifikatRepository = sertifikatRepository;
        this.prodavacSertifikatRepository = prodavacSertifikatRepository;
    }

    @Override
    public Prodavac prijaviProdavca(String korisnickoIme, String sifra) {
        if (korisnickoIme == null) throw new NullPointerException("Korisnicko ime ne sme biti null");
        if (sifra == null) throw new NullPointerException("Sifra ne sme biti null");
        Prodavac prodavac = prodavacRepository.findByKorisnickoIme(korisnickoIme)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Pogresno korisnicko ime ili sifra"));
        if (!prodavac.getSifra().equals(sifra)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Pogresno korisnicko ime ili sifra");
        }
        return prodavac;
    }

    @Override
    public Prodavac sacuvajProdavca(Prodavac prodavac) {
        if (prodavac == null) throw new NullPointerException("Prodavac ne sme biti null");
        return prodavacRepository.save(prodavac);
    }

    @Override
    public List<Prodavac> pretraziProdavce(String pretraga) {
        if (pretraga == null || pretraga.isBlank()) {
            return prodavacRepository.findAll();
        }
        return prodavacRepository.findByImeContainingIgnoreCaseOrPrezimeContainingIgnoreCase(pretraga, pretraga);
    }

    @Override
    public ProdavacSertifikat dodeliSertifikat(Long idProdavac, DodeliSertifikatZahtev zahtev) {
        if (idProdavac == null) throw new NullPointerException("ID prodavca ne sme biti null");
        if (zahtev == null) throw new NullPointerException("Zahtev ne sme biti null");

        Prodavac prodavac = prodavacRepository.findById(idProdavac)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prodavac ne postoji: " + idProdavac));
        Sertifikat sertifikat = sertifikatRepository.findById(zahtev.idSertifikat())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sertifikat ne postoji: " + zahtev.idSertifikat()));

        ProdavacSertifikat zapis = new ProdavacSertifikat(prodavac, sertifikat, zahtev.datum());
        return prodavacSertifikatRepository.save(zapis);
    }
}
