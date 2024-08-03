package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.Quotation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuotationRepository extends JpaRepository<Quotation, Long>, QuerydslPredicateExecutor<Quotation> {
    @Query(value = "select q, qm, count(a) from Quotation q left join QuotationMtrl qm on qm.quotation = q left join Agreement a on a.quotation = q where q.contractor.corno = :corno group by q.qtno, qm", countQuery = "select count(q) from Quotation q")
    Page<Object[]> findQuotationByCorno(@Param("corno") Long corno, Pageable pageable);
}
