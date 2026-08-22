package com.stefansamardzija.alati_projekat.controller;

import com.stefansamardzija.alati_projekat.model.Prodavac;
import com.stefansamardzija.alati_projekat.service.ProdavacService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prodavci")
public class ProdavacController {

    private final ProdavacService prodavacService;

    public ProdavacController(ProdavacService prodavacService) {
        this.prodavacService = prodavacService;
    }

    @GetMapping
    public List<Prodavac> sviProdavci() {
        return prodavacService.sviProdavci();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prodavac> pronadjiPoId(@PathVariable Long id) {
        return ResponseEntity.ok(prodavacService.pronadjiPoId(id));
    }

    @PostMapping
    public ResponseEntity<Prodavac> dodaj(@RequestBody Prodavac prodavac) {
        return ResponseEntity.ok(prodavacService.dodaj(prodavac));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prodavac> izmeni(@PathVariable Long id, @RequestBody Prodavac prodavac) {
        return ResponseEntity.ok(prodavacService.izmeni(id, prodavac));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> obrisi(@PathVariable Long id) {
        prodavacService.obrisi(id);
        return ResponseEntity.noContent().build();
    }
}
