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

    // 정확히 일치하는 Long 값을 찾기
    List<Contractor> findByCorno(Long corno);

    // Long 값을 문자열로 변환하여 부분 일치를 찾기
    @Query("SELECT c FROM Contractor c WHERE str(c.corno) LIKE %:corno%")
    List<Contractor> findByCornoContaining(@Param("corno") String corno);


}
