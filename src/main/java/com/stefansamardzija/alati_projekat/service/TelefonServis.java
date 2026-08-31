package com.stefansamardzija.alati_projekat.service;

import com.stefansamardzija.alati_projekat.entity.Telefon;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interfejs koji definise poslovnu logiku za rad sa telefonima.
 *
 * @author Stefan Samardzija
 * @version 1.0
 */
public interface TelefonServis {

    /**
     * Kreira novi telefon. Identifikator prosledjenog telefona se
     * zanemaruje, odnosno telefon se uvek cuva kao novi zapis.
     *
     * @param telefon Telefon koji se kreira.
     * @return Novokreirani telefon sa dodeljenim identifikatorom.
     * @throws java.lang.NullPointerException Ako je telefon null.
     */
    Telefon kreirajTelefon(Telefon telefon);

    /**
     * Menja podatke postojeceg telefona sa zadatim identifikatorom.
     *
     * @param id Identifikator telefona koji se menja.
     * @param izmene Telefon koji sadrzi izmenjene podatke.
     * @return Izmenjeni telefon.
     * @throws java.lang.NullPointerException Ako je id null ili ako su
     * izmene null.
     * @throws org.springframework.web.server.ResponseStatusException Ako
     * telefon sa zadatim identifikatorom ne postoji (HTTP 404 NOT_FOUND).
     */
    Telefon izmeniTelefon(Long id, Telefon izmene);

    /**
     * Brise telefon sa zadatim identifikatorom.
     *
     * @param id Identifikator telefona koji se brise.
     * @throws java.lang.NullPointerException Ako je id null.
     * @throws org.springframework.web.server.ResponseStatusException Ako
     * telefon sa zadatim identifikatorom ne postoji (HTTP 404 NOT_FOUND).
     */
    void obrisiTelefon(Long id);

    /**
     * Pretrazuje telefone po nazivu. Ako je naziv null ili prazan (odnosno
     * sadrzi samo razmake), vracaju se svi telefoni.
     *
     * @param naziv Naziv ili deo naziva telefona koji se trazi.
     * @return Lista telefona ciji naziv sadrzi zadati tekst (bez obzira na
     * velika i mala slova), odnosno svi telefoni ako naziv nije zadat.
     */
    List<Telefon> pretraziTelefone(String naziv);

    /**
     * Vraca telefon sa zadatim identifikatorom.
     *
     * @param id Identifikator telefona koji se trazi.
     * @return Telefon sa zadatim identifikatorom.
     * @throws java.lang.NullPointerException Ako je id null.
     * @throws org.springframework.web.server.ResponseStatusException Ako
     * telefon sa zadatim identifikatorom ne postoji (HTTP 404 NOT_FOUND).
     */
    Telefon vratiTelefon(Long id);

    /**
     * Vraca sve telefone cija se cena nalazi u zadatom rasponu.
     *
     * @param min Minimalna cena telefona.
     * @param max Maksimalna cena telefona.
     * @return Lista telefona cija je cena u zadatom rasponu.
     * @throws java.lang.NullPointerException Ako je min null ili ako je max
     * null.
     */
    List<Telefon> telefoniPoRasponuCene(BigDecimal min, BigDecimal max);
}
