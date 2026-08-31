package com.stefansamardzija.alati_projekat.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.stefansamardzija.alati_projekat.entity.Telefon;
import com.stefansamardzija.alati_projekat.repository.TelefonRepository;
import com.stefansamardzija.alati_projekat.service.TelefonServis;

import java.math.BigDecimal;
import java.util.List;
/**
 * Implementira TelefonServis koristeci TelefonRepository za pristup
 * podacima o telefonima.
 *
 * @author Stefan Samardzija
 * @version 1.0
 */
@Service
public class TelefonServisImpl implements TelefonServis {

    /** Repozitorijum za pristup podacima o telefonima. */
    private final TelefonRepository telefonRepository;

    /**
     * Kreira novu instancu servisa sa zadatim repozitorijumom.
     *
     * @param telefonRepository Repozitorijum za pristup podacima o telefonima.
     */
    public TelefonServisImpl(TelefonRepository telefonRepository) {
        this.telefonRepository = telefonRepository;
    }

    @Override
    public Telefon kreirajTelefon(Telefon telefon) {
        if (telefon == null) throw new NullPointerException("Telefon ne sme biti null");
        telefon.setIdTelefon(null);
        return telefonRepository.save(telefon);
    }

    @Override
    public Telefon izmeniTelefon(Long id, Telefon izmene) {
        if (id == null) throw new NullPointerException("ID ne sme biti null");
        if (izmene == null) throw new NullPointerException("Izmene ne smeju biti null");
        Telefon telefon = vratiTelefon(id);
        telefon.setNaziv(izmene.getNaziv());
        telefon.setCena(izmene.getCena());
        telefon.setSpecifikacije(izmene.getSpecifikacije());
        return telefonRepository.save(telefon);
    }

    @Override
    public void obrisiTelefon(Long id) {
        if (id == null) throw new NullPointerException("ID ne sme biti null");
        if (!telefonRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Telefon ne postoji: " + id);
        }
        telefonRepository.deleteById(id);
    }

    @Override
    public List<Telefon> pretraziTelefone(String naziv) {
        if (naziv == null || naziv.isBlank()) {
            return telefonRepository.findAll();
        }
        return telefonRepository.findByNazivContainingIgnoreCase(naziv);
    }

    @Override
    public Telefon vratiTelefon(Long id) {
        if (id == null) throw new NullPointerException("ID ne sme biti null");
        return telefonRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Telefon ne postoji: " + id));
    }

    @Override
    public List<Telefon> telefoniPoRasponuCene(BigDecimal min, BigDecimal max) {
        if (min == null) throw new NullPointerException("Minimalna cena ne sme biti null");
        if (max == null) throw new NullPointerException("Maksimalna cena ne sme biti null");
        return telefonRepository.findByCenaBetween(min, max);
    }
}
