package com.glkids.procurehub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.glkids.procurehub.entity.Material;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    @Query("SELECT m FROM Material m JOIN FETCH m.materialGroup JOIN FETCH m.materialWarehouse WHERE m.mtrlno = :mtrlno")
    Optional<Material> findByIdFetch(@Param("mtrlno") Long mtrlno);
}
