package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.Imports;
import com.glkids.procurehub.repository.search.ImportsSearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ImportRepository extends JpaRepository<Imports, Long>, QuerydslPredicateExecutor<Imports>, ImportsSearchRepository {

    @Query("SELECT i FROM Imports i WHERE i.order.quotationmtrl.quotation.contractor.corno = :corno AND FUNCTION('DATE', i.approvedate) = :approvedate AND i.status = :status")
    List<Imports> getImportForm(@Param("corno") Long corno, @Param("approvedate") LocalDate approvedate, @Param("status") Integer status);

    @Modifying
    @Query("update Imports i set i.status = :status where i.importno = :importno")
    Integer changeStatus(@Param("importno") Long importno, @Param("status") Integer status);
}
