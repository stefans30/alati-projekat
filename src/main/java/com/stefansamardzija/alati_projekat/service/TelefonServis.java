package com.stefansamardzija.alati_projekat.service;

import com.stefansamardzija.alati_projekat.entity.Telefon;

import java.math.BigDecimal;
import java.util.List;

public interface TelefonServis {

    Telefon kreirajTelefon(Telefon telefon);

    Telefon izmeniTelefon(Long id, Telefon izmene);

    void obrisiTelefon(Long id);

    List<Telefon> pretraziTelefone(String naziv);

    Telefon vratiTelefon(Long id);

    List<Telefon> telefoniPoRasponuCene(BigDecimal min, BigDecimal max);
}
