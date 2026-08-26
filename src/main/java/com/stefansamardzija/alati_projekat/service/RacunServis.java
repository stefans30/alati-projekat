package com.stefansamardzija.alati_projekat.service;

import com.stefansamardzija.alati_projekat.dto.KreirajRacunZahtev;
import com.stefansamardzija.alati_projekat.entity.Racun;

import java.util.List;

public interface RacunServis {

    Racun kreirajRacun(KreirajRacunZahtev zahtev);

    List<Racun> pretraziRacune();

    Racun vratiRacun(Long id);

    List<Racun> racuniZaProdavca(Long idProdavac);

    List<Racun> racuniZaKupca(Long idKupac);
}
