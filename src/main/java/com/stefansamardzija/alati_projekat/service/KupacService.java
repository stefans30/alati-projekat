package com.stefansamardzija.alati_projekat.service;

import com.stefansamardzija.alati_projekat.exception.EntityNotFoundException;
import com.stefansamardzija.alati_projekat.exception.NevalidniPodaciException;
import com.stefansamardzija.alati_projekat.model.FizickoLice;
import com.stefansamardzija.alati_projekat.model.Kupac;
import com.stefansamardzija.alati_projekat.model.Mesto;
import com.stefansamardzija.alati_projekat.model.PravnoLice;
import com.stefansamardzija.alati_projekat.repository.KupacRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KupacService {

    private final KupacRepository kupacRepository;
    private final MestoService mestoService;

    public KupacService(KupacRepository kupacRepository, MestoService mestoService) {
        this.kupacRepository = kupacRepository;
        this.mestoService = mestoService;
    }

    public List<Kupac> sviKupci() {
        return kupacRepository.findAll();
    }

    public Kupac pronadjiPoId(Long id) {
        return kupacRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Kupac sa id " + id + " ne postoji"));
    }

    public FizickoLice registrujFizickoLice(String mejl, Long idMesta, String ime, String prezime, String jmbg) {
        validirajMejl(mejl);
        if (ime == null || ime.isBlank() || prezime == null || prezime.isBlank()) {
            throw new NevalidniPodaciException("Ime i prezime ne smeju biti prazni");
        }
        if (jmbg == null || jmbg.length() != 13) {
            throw new NevalidniPodaciException("JMBG mora imati tacno 13 cifara");
        }
        Mesto mesto = mestoService.pronadjiPoId(idMesta);
        FizickoLice fizickoLice = new FizickoLice(mejl, mesto, ime, prezime, jmbg);
        return (FizickoLice) kupacRepository.save(fizickoLice);
    }

    public PravnoLice registrujPravnoLice(String mejl, Long idMesta, String nazivFirme, String pib) {
        validirajMejl(mejl);
        if (nazivFirme == null || nazivFirme.isBlank()) {
            throw new NevalidniPodaciException("Naziv firme ne sme biti prazan");
        }
        if (pib == null || pib.length() != 9) {
            throw new NevalidniPodaciException("PIB mora imati tacno 9 cifara");
        }
        Mesto mesto = mestoService.pronadjiPoId(idMesta);
        PravnoLice pravnoLice = new PravnoLice(mejl, mesto, nazivFirme, pib);
        return (PravnoLice) kupacRepository.save(pravnoLice);
    }

    public void obrisi(Long id) {
        Kupac kupac = pronadjiPoId(id);
        kupacRepository.delete(kupac);
    }

    private void validirajMejl(String mejl) {
        if (mejl == null || !mejl.contains("@")) {
            throw new NevalidniPodaciException("Mejl adresa nije validna");
        }
    }
}
