package com.stefansamardzija.alati_projekat.service;

import com.stefansamardzija.alati_projekat.entity.Kupac;

import java.util.List;

/**
 * Interfejs koji definise poslovnu logiku za rad sa kupcima. Kupac moze
 * biti fizicko ili pravno lice.
 *
 * @author Stefan Samardzija
 * @version 1.0
 */
public interface KupacServis {

    /**
     * Kreira novog kupca. Identifikator prosledjenog kupca se zanemaruje,
     * odnosno kupac se uvek cuva kao novi zapis.
     *
     * @param kupac Kupac koji se kreira.
     * @return Novokreirani kupac sa dodeljenim identifikatorom.
     * @throws java.lang.NullPointerException Ako je kupac null.
     */
    Kupac kreirajKupca(Kupac kupac);

    /**
     * Menja podatke postojeceg kupca sa zadatim identifikatorom. Tip kupca
     * (fizicko ili pravno lice) ne moze biti promenjen ovom operacijom.
     *
     * @param id Identifikator kupca koji se menja.
     * @param izmene Kupac koji sadrzi izmenjene podatke.
     * @return Izmenjeni kupac.
     * @throws java.lang.NullPointerException Ako je id null ili ako su
     * izmene null.
     * @throws org.springframework.web.server.ResponseStatusException Ako
     * kupac sa zadatim identifikatorom ne postoji (HTTP 404 NOT_FOUND), ili
     * ako se tip postojeceg kupca razlikuje od tipa prosledjenih izmena
     * (HTTP 400 BAD_REQUEST).
     */
    Kupac izmeniKupca(Long id, Kupac izmene);

    /**
     * Brise kupca sa zadatim identifikatorom.
     *
     * @param id Identifikator kupca koji se brise.
     * @throws java.lang.NullPointerException Ako je id null.
     * @throws org.springframework.web.server.ResponseStatusException Ako
     * kupac sa zadatim identifikatorom ne postoji (HTTP 404 NOT_FOUND).
     */
    void obrisiKupca(Long id);

    /**
     * Vraca sve kupce.
     *
     * @return Lista svih kupaca.
     */
    List<Kupac> pretraziKupce();

    /**
     * Vraca kupca sa zadatim identifikatorom.
     *
     * @param id Identifikator kupca koji se trazi.
     * @return Kupac sa zadatim identifikatorom.
     * @throws java.lang.NullPointerException Ako je id null.
     * @throws org.springframework.web.server.ResponseStatusException Ako
     * kupac sa zadatim identifikatorom ne postoji (HTTP 404 NOT_FOUND).
     */
    Kupac vratiKupca(Long id);

    /**
     * Vraca sve kupce koji pripadaju mestu sa zadatim identifikatorom.
     *
     * @param idMesto Identifikator mesta po kome se kupci filtriraju.
     * @return Lista kupaca koji pripadaju zadatom mestu.
     * @throws java.lang.NullPointerException Ako je idMesto null.
     */
    List<Kupac> kupciPoMestu(Long idMesto);

    /**
     * Vraca sve kupce zadatog tipa.
     *
     * @param tip Tip kupca po kome se kupci filtriraju.
     * @return Lista kupaca zadatog tipa.
     * @throws java.lang.NullPointerException Ako je tip null.
     */
    List<Kupac> kupciPoTipu(String tip);
}
