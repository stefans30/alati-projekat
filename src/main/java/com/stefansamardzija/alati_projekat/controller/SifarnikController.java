package com.stefansamardzija.alati_projekat.controller;

import org.springframework.web.bind.annotation.*;
import com.stefansamardzija.alati_projekat.entity.Mesto;
import com.stefansamardzija.alati_projekat.entity.Sertifikat;
import com.stefansamardzija.alati_projekat.service.SifarnikServis;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SifarnikController {

    private final SifarnikServis sifarnikServis;

    public SifarnikController(SifarnikServis sifarnikServis) {
        this.sifarnikServis = sifarnikServis;
    }

    @GetMapping("/mesta")
    public List<Mesto> mesta() {
        return sifarnikServis.ucitajMesta();
    }

    @PostMapping("/mesta")
    public Mesto kreirajMesto(@RequestBody Mesto mesto) {
        return sifarnikServis.kreirajMesto(mesto);
    }

    @GetMapping("/sertifikati")
    public List<Sertifikat> sertifikati() {
        return sifarnikServis.ucitajSertifikate();
    }

    @PostMapping("/sertifikati")
    public Sertifikat kreirajSertifikat(@RequestBody Sertifikat sertifikat) {
        return sifarnikServis.kreirajSertifikat(sertifikat);
    }
}
