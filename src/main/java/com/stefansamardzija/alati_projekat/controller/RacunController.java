package com.stefansamardzija.alati_projekat.controller;

import com.stefansamardzija.alati_projekat.model.Racun;
import com.stefansamardzija.alati_projekat.service.RacunService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST kontroler za operacije nad računima.
 */
@RestController
@RequestMapping("/api/racuni")
public class RacunController {

    private final RacunService racunService;

    public RacunController(RacunService racunService) {
        this.racunService = racunService;
    }

    @GetMapping
    public List<Racun> sviRacuni() {
        return racunService.sviRacuni();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Racun> pronadjiPoId(@PathVariable Long id) {
        return ResponseEntity.ok(racunService.pronadjiPoId(id));
    }

    @PostMapping("/zakljuci")
    public ResponseEntity<Racun> zakljuciRacun(
            @RequestParam Long idProdavac, @RequestParam Long idKupac) {
        return ResponseEntity.ok(racunService.zakljuciRacun(idProdavac, idKupac));
    }

    @PostMapping("/{idRacun}/stavke")
    public ResponseEntity<Racun> dodajStavku(
            @PathVariable Long idRacun,
            @RequestParam Long idTelefon,
            @RequestParam int kolicina) {
        return ResponseEntity.ok(racunService.dodajStavku(idRacun, idTelefon, kolicina));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> obrisi(@PathVariable Long id) {
        racunService.obrisi(id);
        return ResponseEntity.noContent().build();
    }
}
