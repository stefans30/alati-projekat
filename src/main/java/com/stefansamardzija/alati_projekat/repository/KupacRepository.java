package com.stefansamardzija.alati_projekat.repository;

import com.stefansamardzija.alati_projekat.entity.Kupac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KupacRepository extends JpaRepository<Kupac, Long> {

    List<Kupac> findByMesto_IdMesto(Long idMesto);

    @Query(value = "SELECT * FROM kupac WHERE UPPER(tip_kupca) = UPPER(:tip)", nativeQuery = true)
    List<Kupac> findByTipKupca(@Param("tip") String tip);
}
