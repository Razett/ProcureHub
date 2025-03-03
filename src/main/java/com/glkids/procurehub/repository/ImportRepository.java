package com.glkids.procurehub.repository;

import com.glkids.procurehub.dto.EmpCountDTO;
import com.glkids.procurehub.entity.Imports;
import com.glkids.procurehub.repository.search.ImportsSearchRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ImportRepository extends JpaRepository<Imports, Long>, QuerydslPredicateExecutor<Imports>, ImportsSearchRepository {

    @Query("SELECT i FROM Imports i WHERE i.order.quotationmtrl.quotation.contractor.corno = :corno AND FUNCTION('DATE', i.approvedate) = :approvedate AND i.status = :status")
    List<Imports> getImportForm(@Param("corno") Long corno, @Param("approvedate") LocalDate approvedate, @Param("status") Integer status);

    @Modifying
    @Query("update Imports i set i.status = :status where i.importno = :importno")
    Integer changeStatus(@Param("importno") Long importno, @Param("status") Integer status);

    long countByStatusIn(List<Integer> statuses);

    @Transactional
    @Modifying
    @Query("UPDATE Imports i SET i.quantity = :quantity WHERE i.importno = :importno")
    void updateQuantityByImportno(@Param("importno") Long importno, @Param("quantity") Long quantity);

    @Query("SELECT FUNCTION('DATE_FORMAT', im.approvedate, '%Y-%m') AS month, COUNT(im.importno) AS counts " +
            "FROM Imports im " +
            "WHERE im.status > 5 AND im.status < 8 " +
            "AND im.approvedate >= :startDate " +
            "GROUP BY FUNCTION('DATE_FORMAT', im.approvedate, '%Y-%m') ")
    List<Object[]> findMonthlyImportCount(@Param("startDate") LocalDateTime startDate);

}
