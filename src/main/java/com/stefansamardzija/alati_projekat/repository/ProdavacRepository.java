package com.stefansamardzija.alati_projekat.repository;

import com.stefansamardzija.alati_projekat.entity.Prodavac;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdavacRepository extends JpaRepository<Prodavac, Long> {

    Optional<Prodavac> findByKorisnickoIme(String korisnickoIme);

    List<Prodavac> findByImeContainingIgnoreCaseOrPrezimeContainingIgnoreCase(String ime, String prezime);
}
