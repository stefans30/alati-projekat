package com.stefansamardzija.alati_projekat.controller;

import com.stefansamardzija.alati_projekat.dto.FizickoLiceDTO;
import com.stefansamardzija.alati_projekat.dto.PravnoLiceDTO;
import com.stefansamardzija.alati_projekat.model.FizickoLice;
import com.stefansamardzija.alati_projekat.model.Kupac;
import com.stefansamardzija.alati_projekat.model.PravnoLice;
import com.stefansamardzija.alati_projekat.service.KupacService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kupci")
public class KupacController {

    private final KupacService kupacService;

    public KupacController(KupacService kupacService) {
        this.kupacService = kupacService;
    }

    @GetMapping
    public List<Kupac> sviKupci() {
        return kupacService.sviKupci();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kupac> pronadjiPoId(@PathVariable Long id) {
        return ResponseEntity.ok(kupacService.pronadjiPoId(id));
    }

    @PostMapping("/fizicko-lice")
    public ResponseEntity<FizickoLice> registrujFizickoLice(@RequestBody FizickoLiceDTO dto) {
        FizickoLice kreiran = kupacService.registrujFizickoLice(
                dto.mejl(), dto.idMesta(), dto.ime(), dto.prezime(), dto.jmbg());
        return ResponseEntity.ok(kreiran);
    }

    @PostMapping("/pravno-lice")
    public ResponseEntity<PravnoLice> registrujPravnoLice(@RequestBody PravnoLiceDTO dto) {
        PravnoLice kreiran = kupacService.registrujPravnoLice(
                dto.mejl(), dto.idMesta(), dto.nazivFirme(), dto.pib());
        return ResponseEntity.ok(kreiran);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> obrisi(@PathVariable Long id) {
        kupacService.obrisi(id);
        return ResponseEntity.noContent().build();
    }
}
