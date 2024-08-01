package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContractorRepository extends JpaRepository<Contractor, Long> {

    @Query("SELECT c FROM Contractor c WHERE c.name = :name")
    Optional<Contractor> findByName(@Param("name") String name);

    List<Contractor> findByNameContaining(String name);

}
