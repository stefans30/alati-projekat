package com.stefansamardzija.alati_projekat.service;

import com.stefansamardzija.alati_projekat.entity.Kupac;

import java.util.List;

public interface KupacServis {

    Kupac kreirajKupca(Kupac kupac);

    Kupac izmeniKupca(Long id, Kupac izmene);

    void obrisiKupca(Long id);

    List<Kupac> pretraziKupce();

    Kupac vratiKupca(Long id);

    List<Kupac> kupciPoMestu(Long idMesto);

    List<Kupac> kupciPoTipu(String tip);
}
