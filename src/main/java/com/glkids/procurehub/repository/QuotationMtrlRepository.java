package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.QuotationMtrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuotationMtrlRepository extends JpaRepository<QuotationMtrl, Long> , QuerydslPredicateExecutor<QuotationMtrl> {

    @Query("select qm from QuotationMtrl qm where qm.material.mtrlno = :mtrlno and qm.quotation.status=2")
    List<QuotationMtrl> findByMaterial(@Param("mtrlno") Long mtrlno);
}
