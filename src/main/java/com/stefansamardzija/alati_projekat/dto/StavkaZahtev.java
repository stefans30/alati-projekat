package com.stefansamardzija.alati_projekat.dto;

import java.math.BigDecimal;

public record StavkaZahtev(Long idTelefon, int kolicina, BigDecimal cena) {
}
