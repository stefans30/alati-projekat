package com.stefansamardzija.alati_projekat.repository;

import com.stefansamardzija.alati_projekat.entity.Telefon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface TelefonRepository extends JpaRepository<Telefon, Long> {

    List<Telefon> findByNazivContainingIgnoreCase(String naziv);

    List<Telefon> findByCenaBetween(BigDecimal min, BigDecimal max);
}
