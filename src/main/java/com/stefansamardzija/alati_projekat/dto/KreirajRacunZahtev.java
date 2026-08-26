package com.stefansamardzija.alati_projekat.dto;

import java.util.List;

public record KreirajRacunZahtev(Long idProdavac, Long idKupac, List<StavkaZahtev> stavke) {
}
