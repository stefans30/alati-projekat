package com.stefansamardzija.alati_projekat.repository;

import com.stefansamardzija.alati_projekat.model.Prodavac;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdavacRepository extends JpaRepository<Prodavac, Long> {
}
