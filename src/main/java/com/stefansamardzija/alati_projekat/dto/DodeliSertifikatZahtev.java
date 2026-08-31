package com.stefansamardzija.alati_projekat.dto;

import java.time.LocalDate;

/** Zahtev za dodelu sertifikata prodavcu. */
public record DodeliSertifikatZahtev(Long idSertifikat, LocalDate datum) {
}
