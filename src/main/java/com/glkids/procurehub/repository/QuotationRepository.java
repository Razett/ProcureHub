package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.Emp;
import com.glkids.procurehub.entity.Quotation;
import com.glkids.procurehub.repository.search.QuotationSearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface QuotationRepository extends JpaRepository<Quotation, Long>, QuerydslPredicateExecutor<Quotation>, QuotationSearchRepository {
    @Query(value = "select q, qm, count(a) from Quotation q left join QuotationMtrl qm on qm.quotation = q left join Agreement a on a.quotation = q where q.contractor.corno = :corno group by q.qtno, qm", countQuery = "select count(q) from Quotation q")
    Page<Object[]> findQuotationByCorno(@Param("corno") Long corno, Pageable pageable);

    @Query(value = "select q, qm, count(a) from Quotation q left join QuotationMtrl qm on qm.quotation = q left join Agreement a on a.quotation = q where qm.material.mtrlno = :mtrlno group by q.qtno, qm", countQuery = "select count(q) from Quotation q")
    Page<Object[]> findQuotationByMtrlno(@Param("mtrlno") Long mtrlno, Pageable pageable);

    @Modifying
    @Query("UPDATE Quotation q SET q.status = :status WHERE q.qtno = :qtno")
    void changeStatus(@Param("qtno") Long qtno, @Param("status") Integer status);

}
