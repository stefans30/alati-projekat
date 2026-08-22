package com.stefansamardzija.alati_projekat.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String poruka) {
        super(poruka);
    }
}
