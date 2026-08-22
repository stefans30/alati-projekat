package com.stefansamardzija.alati_projekat.controller;

import com.stefansamardzija.alati_projekat.model.Mesto;
import com.stefansamardzija.alati_projekat.service.MestoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesta")
public class MestoController {

    private final MestoService mestoService;

    public MestoController(MestoService mestoService) {
        this.mestoService = mestoService;
    }

    @GetMapping
    public List<Mesto> sviMesta() {
        return mestoService.svaMesta();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mesto> pronadjiPoId(@PathVariable Long id) {
        return ResponseEntity.ok(mestoService.pronadjiPoId(id));
    }

    @PostMapping
    public ResponseEntity<Mesto> dodaj(@RequestBody Mesto mesto) {
        return ResponseEntity.ok(mestoService.dodaj(mesto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mesto> izmeni(@PathVariable Long id, @RequestBody Mesto mesto) {
        return ResponseEntity.ok(mestoService.izmeni(id, mesto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> obrisi(@PathVariable Long id) {
        mestoService.obrisi(id);
        return ResponseEntity.noContent().build();
    }
}
