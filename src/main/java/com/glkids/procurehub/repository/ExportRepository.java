package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.Export;
import com.glkids.procurehub.repository.search.ExportSearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ExportRepository extends JpaRepository<Export, Long>, QuerydslPredicateExecutor<Export>, ExportSearchRepository {

    long countByStatusIn(List<Integer> statuses);

    @Query(value = "SELECT DATE_FORMAT(e.shippeddate, '%Y-%m') AS month, SUM(e.quantity) AS quantity " +
            "FROM export e " +
            "LEFT JOIN `prcr` p ON e.prcr_prcrno = p.prcrno " +
            "WHERE p.material_mtrlno = :mtrlno " +
            "AND e.status > 3 AND e.status < 6 " +
            "AND e.shippeddate >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH) " +
            "GROUP BY DATE_FORMAT(e.shippeddate, '%Y-%m') " +
            "ORDER BY month DESC", nativeQuery = true)
    List<Object[]> findMonthlyExportData(Long mtrlno);

    @Query("SELECT FUNCTION('DATE_FORMAT', ex.shippeddate, '%Y-%m') AS month, COUNT(ex.shippeddate) AS counts " +
            "FROM Export ex " +
            "WHERE ex.status > 3 " +
            "AND ex.shippeddate >= :startDate " +
            "GROUP BY FUNCTION('DATE_FORMAT', ex.shippeddate, '%Y-%m') ")
    List<Object[]> findMonthlyExportCount(@Param("startDate") LocalDateTime startDate);
}
