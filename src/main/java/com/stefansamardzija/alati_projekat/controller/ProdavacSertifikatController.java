package com.stefansamardzija.alati_projekat.controller;

import com.stefansamardzija.alati_projekat.model.ProdavacSertifikat;
import com.stefansamardzija.alati_projekat.service.ProdavacSertifikatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST kontroler za dodeljivanje sertifikata prodavcima.
 */
@RestController
@RequestMapping("/api/prodavac-sertifikati")
public class ProdavacSertifikatController {

    private final ProdavacSertifikatService service;

    public ProdavacSertifikatController(ProdavacSertifikatService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProdavacSertifikat> sviZapisi() {
        return service.sviZapisi();
    }

    @PostMapping("/dodeli")
    public ResponseEntity<ProdavacSertifikat> dodeliSertifikat(
            @RequestParam Long idProdavac, @RequestParam Long idSertifikat) {
        return ResponseEntity.ok(service.dodeliSertifikat(idProdavac, idSertifikat));
    }
}