package com.stefansamardzija.alati_projekat.dto;

import java.math.BigDecimal;

/**
 * Jedna stavka u zahtevu za kreiranje racuna.
 * cena je opciona; ako je null, uzima se trenutna cena telefona.
 */
public record StavkaZahtev(Long idTelefon, int kolicina, BigDecimal cena) {
}
