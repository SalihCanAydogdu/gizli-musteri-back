package com.example.gizli_musteri_back.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.gizli_musteri_back.entities.GizliMusteri;

public interface GizliMusteriRepository extends JpaRepository<GizliMusteri,Long> {
	

    @Query("SELECT gm FROM GizliMusteri gm WHERE gm.kaydolmaTarihi BETWEEN :startDate AND :endDate")
    List<GizliMusteri> findByKaydolmaTarihiBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	
    boolean existsByEmail(String email);

}
