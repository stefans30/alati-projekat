package com.stefansamardzija.alati_projekat.exception;

public class NevalidniPodaciException extends RuntimeException {

    public NevalidniPodaciException(String poruka) {
        super(poruka);
    }
}