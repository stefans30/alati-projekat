package com.stefansamardzija.alati_projekat.service;

import com.stefansamardzija.alati_projekat.dto.DodeliSertifikatZahtev;
import com.stefansamardzija.alati_projekat.entity.Prodavac;
import com.stefansamardzija.alati_projekat.entity.ProdavacSertifikat;

import java.util.List;

public interface ProdavacServis {

    Prodavac prijaviProdavca(String korisnickoIme, String sifra);

    Prodavac sacuvajProdavca(Prodavac prodavac);

    List<Prodavac> pretraziProdavce(String pretraga);

    ProdavacSertifikat dodeliSertifikat(Long idProdavac, DodeliSertifikatZahtev zahtev);
}
