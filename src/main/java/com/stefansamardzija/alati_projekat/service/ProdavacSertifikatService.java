package com.stefansamardzija.alati_projekat.service;

import com.stefansamardzija.alati_projekat.model.Prodavac;
import com.stefansamardzija.alati_projekat.model.ProdavacSertifikat;
import com.stefansamardzija.alati_projekat.model.Sertifikat;
import com.stefansamardzija.alati_projekat.repository.ProdavacSertifikatRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProdavacSertifikatService {

    private final ProdavacSertifikatRepository repository;
    private final ProdavacService prodavacService;
    private final SertifikatService sertifikatService;

    public ProdavacSertifikatService(ProdavacSertifikatRepository repository,
                                     ProdavacService prodavacService,
                                     SertifikatService sertifikatService) {
        this.repository = repository;
        this.prodavacService = prodavacService;
        this.sertifikatService = sertifikatService;
    }

    public List<ProdavacSertifikat> sviZapisi() {
        return repository.findAll();
    }

    public ProdavacSertifikat dodeliSertifikat(Long idProdavac, Long idSertifikat) {
        Prodavac prodavac = prodavacService.pronadjiPoId(idProdavac);
        Sertifikat sertifikat = sertifikatService.pronadjiPoId(idSertifikat);

        ProdavacSertifikat zapis = new ProdavacSertifikat(LocalDate.now(), prodavac, sertifikat);
        return repository.save(zapis);
    }
}