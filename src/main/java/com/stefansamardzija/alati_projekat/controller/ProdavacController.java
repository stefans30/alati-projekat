package com.stefansamardzija.alati_projekat.controller;

import org.springframework.web.bind.annotation.*;
import com.stefansamardzija.alati_projekat.dto.DodeliSertifikatZahtev;
import com.stefansamardzija.alati_projekat.dto.PrijavaZahtev;
import com.stefansamardzija.alati_projekat.entity.Prodavac;
import com.stefansamardzija.alati_projekat.entity.ProdavacSertifikat;
import com.stefansamardzija.alati_projekat.service.ProdavacServis;

import java.util.List;

@RestController
@RequestMapping("/api/prodavci")
public class ProdavacController {

    private final ProdavacServis prodavacServis;

    public ProdavacController(ProdavacServis prodavacServis) {
        this.prodavacServis = prodavacServis;
    }

    @PostMapping("/prijava")
    public Prodavac prijava(@RequestBody PrijavaZahtev zahtev) {
        return prodavacServis.prijaviProdavca(zahtev.korisnickoIme(), zahtev.sifra());
    }

    @PostMapping
    public Prodavac kreiraj(@RequestBody Prodavac prodavac) {
        return prodavacServis.sacuvajProdavca(prodavac);
    }

    @GetMapping
    public List<Prodavac> pretrazi(@RequestParam(required = false) String pretraga) {
        return prodavacServis.pretraziProdavce(pretraga);
    }

    @PostMapping("/{id}/sertifikati")
    public ProdavacSertifikat dodeliSertifikat(@PathVariable Long id, @RequestBody DodeliSertifikatZahtev zahtev) {
        return prodavacServis.dodeliSertifikat(id, zahtev);
    }
}
