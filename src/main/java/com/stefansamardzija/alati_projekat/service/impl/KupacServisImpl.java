package com.stefansamardzija.alati_projekat.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.stefansamardzija.alati_projekat.entity.FizickoLice;
import com.stefansamardzija.alati_projekat.entity.Kupac;
import com.stefansamardzija.alati_projekat.entity.PravnoLice;
import com.stefansamardzija.alati_projekat.repository.KupacRepository;
import com.stefansamardzija.alati_projekat.service.KupacServis;

import java.util.List;

/**
 * Implementira KupacServis koristeci KupacRepository za pristup
 * podacima o kupcima.
 *
 * @author Stefan Samardzija
 * @version 1.0
 */
@Service
public class KupacServisImpl implements KupacServis {

    /** Repozitorijum za pristup podacima o kupcima. */
    private final KupacRepository kupacRepository;

    /**
     * Kreira novu instancu servisa sa zadatim repozitorijumom.
     *
     * @param kupacRepository Repozitorijum za pristup podacima o kupcima.
     */
    public KupacServisImpl(KupacRepository kupacRepository) {
        this.kupacRepository = kupacRepository;
    }

    @Override
    public Kupac kreirajKupca(Kupac kupac) {
        if (kupac == null) throw new NullPointerException("Kupac ne sme biti null");
        kupac.setIdKupac(null);
        return kupacRepository.save(kupac);
    }

    @Override
    public Kupac izmeniKupca(Long id, Kupac izmene) {
        if (id == null) throw new NullPointerException("ID ne sme biti null");
        if (izmene == null) throw new NullPointerException("Izmene ne smeju biti null");
        Kupac postojeci = vratiKupca(id);
        if (!postojeci.getClass().equals(izmene.getClass())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ne moze se promeniti tip kupca");
        }
        postojeci.setMejl(izmene.getMejl());
        postojeci.setMesto(izmene.getMesto());

        if (postojeci instanceof FizickoLice fl && izmene instanceof FizickoLice izmFl) {
            fl.setIme(izmFl.getIme());
            fl.setPrezime(izmFl.getPrezime());
            fl.setJmbg(izmFl.getJmbg());
        } else if (postojeci instanceof PravnoLice pl && izmene instanceof PravnoLice izmPl) {
            pl.setNazivFirme(izmPl.getNazivFirme());
            pl.setPib(izmPl.getPib());
        }
        return kupacRepository.save(postojeci);
    }

    @Override
    public void obrisiKupca(Long id) {
        if (id == null) throw new NullPointerException("ID ne sme biti null");
        if (!kupacRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kupac ne postoji: " + id);
        }
        kupacRepository.deleteById(id);
    }

    @Override
    public List<Kupac> pretraziKupce() {
        return kupacRepository.findAll();
    }

    @Override
    public Kupac vratiKupca(Long id) {
        if (id == null) throw new NullPointerException("ID ne sme biti null");
        return kupacRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kupac ne postoji: " + id));
    }

    @Override
    public List<Kupac> kupciPoMestu(Long idMesto) {
        if (idMesto == null) throw new NullPointerException("ID mesta ne sme biti null");
        return kupacRepository.findByMesto_IdMesto(idMesto);
    }

    @Override
    public List<Kupac> kupciPoTipu(String tip) {
        if (tip == null) throw new NullPointerException("Tip kupca ne sme biti null");
        return kupacRepository.findByTipKupca(tip);
    }
}
