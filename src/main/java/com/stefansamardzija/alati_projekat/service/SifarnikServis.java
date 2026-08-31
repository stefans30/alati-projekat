package com.stefansamardzija.alati_projekat.service;

import com.stefansamardzija.alati_projekat.entity.Mesto;
import com.stefansamardzija.alati_projekat.entity.Sertifikat;

import java.util.List;

/**
 * Interfejs koji definise poslovnu logiku za rad sa sifarnicima
 * (mesta i sertifikati).
 *
 * @author Stefan Samardzija
 * @version 1.0
 */
public interface SifarnikServis {

    /**
     * Vraca sva mesta.
     *
     * @return Lista svih mesta.
     */
    List<Mesto> ucitajMesta();

    /**
     * Kreira novo mesto.
     *
     * @param mesto Mesto koje se kreira.
     * @return Novokreirano mesto.
     * @throws java.lang.NullPointerException Ako je mesto null.
     */
    Mesto kreirajMesto(Mesto mesto);

    /**
     * Vraca sve sertifikate.
     *
     * @return Lista svih sertifikata.
     */
    List<Sertifikat> ucitajSertifikate();

    /**
     * Kreira novi sertifikat.
     *
     * @param sertifikat Sertifikat koji se kreira.
     * @return Novokreirani sertifikat.
     * @throws java.lang.NullPointerException Ako je sertifikat null.
     */
    Sertifikat kreirajSertifikat(Sertifikat sertifikat);
}
