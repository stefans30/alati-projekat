package com.stefansamardzija.alati_projekat.service;

import com.stefansamardzija.alati_projekat.entity.Mesto;
import com.stefansamardzija.alati_projekat.entity.Sertifikat;

import java.util.List;

public interface SifarnikServis {

    List<Mesto> ucitajMesta();

    Mesto kreirajMesto(Mesto mesto);

    List<Sertifikat> ucitajSertifikate();

    Sertifikat kreirajSertifikat(Sertifikat sertifikat);
}
