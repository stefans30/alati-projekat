package com.stefansamardzija.alati_projekat.repository;

import com.stefansamardzija.alati_projekat.entity.Mesto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MestoRepository extends JpaRepository<Mesto, Long> {
}
