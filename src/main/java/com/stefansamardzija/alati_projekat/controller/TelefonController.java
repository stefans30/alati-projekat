package com.stefansamardzija.alati_projekat.controller;

import org.springframework.web.bind.annotation.*;
import com.stefansamardzija.alati_projekat.entity.Telefon;
import com.stefansamardzija.alati_projekat.service.TelefonServis;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/telefoni")
public class TelefonController {

    private final TelefonServis telefonServis;

    public TelefonController(TelefonServis telefonServis) {
        this.telefonServis = telefonServis;
    }

    @PostMapping
    public Telefon kreiraj(@RequestBody Telefon telefon) {
        return telefonServis.kreirajTelefon(telefon);
    }

    @PutMapping("/{id}")
    public Telefon izmeni(@PathVariable Long id, @RequestBody Telefon telefon) {
        return telefonServis.izmeniTelefon(id, telefon);
    }

    @DeleteMapping("/{id}")
    public void obrisi(@PathVariable Long id) {
        telefonServis.obrisiTelefon(id);
    }

    @GetMapping
    public List<Telefon> pretrazi(@RequestParam(required = false) String naziv) {
        return telefonServis.pretraziTelefone(naziv);
    }

    @GetMapping("/{id}")
    public Telefon vrati(@PathVariable Long id) {
        return telefonServis.vratiTelefon(id);
    }

    @GetMapping("/po-ceni")
    public List<Telefon> poRasponuCene(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return telefonServis.telefoniPoRasponuCene(min, max);
    }
}
