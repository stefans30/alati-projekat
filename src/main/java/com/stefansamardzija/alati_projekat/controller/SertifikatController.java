package com.stefansamardzija.alati_projekat.controller;

import com.stefansamardzija.alati_projekat.model.Sertifikat;
import com.stefansamardzija.alati_projekat.service.SertifikatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sertifikati")
public class SertifikatController {

    private final SertifikatService sertifikatService;

    public SertifikatController(SertifikatService sertifikatService) {
        this.sertifikatService = sertifikatService;
    }

    @GetMapping
    public List<Sertifikat> sviSertifikati() {
        return sertifikatService.sviSertifikati();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sertifikat> pronadjiPoId(@PathVariable Long id) {
        return ResponseEntity.ok(sertifikatService.pronadjiPoId(id));
    }

    @PostMapping
    public ResponseEntity<Sertifikat> dodaj(@RequestBody Sertifikat sertifikat) {
        return ResponseEntity.ok(sertifikatService.dodaj(sertifikat));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> obrisi(@PathVariable Long id) {
        sertifikatService.obrisi(id);
        return ResponseEntity.noContent().build();
    }
}
