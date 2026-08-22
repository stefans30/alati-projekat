package com.stefansamardzija.alati_projekat.controller;

import com.stefansamardzija.alati_projekat.model.Telefon;
import com.stefansamardzija.alati_projekat.service.TelefonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/telefoni")
public class TelefonController {

    private final TelefonService telefonService;

    public TelefonController(TelefonService telefonService) {
        this.telefonService = telefonService;
    }

    @GetMapping
    public List<Telefon> sviTelefoni() {
        return telefonService.sviTelefoni();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Telefon> pronadjiPoId(@PathVariable Long id) {
        return ResponseEntity.ok(telefonService.pronadjiPoId(id));
    }

    @PostMapping
    public ResponseEntity<Telefon> dodaj(@RequestBody Telefon telefon) {
        return ResponseEntity.ok(telefonService.dodaj(telefon));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Telefon> izmeni(@PathVariable Long id, @RequestBody Telefon telefon) {
        return ResponseEntity.ok(telefonService.izmeni(id, telefon));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> obrisi(@PathVariable Long id) {
        telefonService.obrisi(id);
        return ResponseEntity.noContent().build();
    }
}
