package com.stefansamardzija.alati_projekat.repository;

import com.stefansamardzija.alati_projekat.entity.Racun;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RacunRepository extends JpaRepository<Racun, Long> {

    List<Racun> findByProdavac_IdProdavac(Long idProdavac);

    List<Racun> findByKupac_IdKupac(Long idKupac);
}
