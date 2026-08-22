package com.stefansamardzija.alati_projekat.repository;

import com.stefansamardzija.alati_projekat.model.Kupac;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KupacRepository extends JpaRepository<Kupac, Long> {
}
