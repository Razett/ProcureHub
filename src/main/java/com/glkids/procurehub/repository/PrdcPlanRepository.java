package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.PrdcPlan;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface PrdcPlanRepository extends JpaRepository<PrdcPlan, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE PrdcPlan p SET p.quantity = :quantity WHERE p.prdcPlanNo = :prdcPlanNo")
    int updateQuantityByPrdcPlanNo(@Param("quantity") Long quantity, @Param("prdcPlanNo") Long prdcPlanNo);

    @Query("SELECT p.prdc.name AS prdcName, SUM(p.quantity) AS totalQuantity " +
            "FROM PrdcPlan p " +
            "WHERE p.startdate BETWEEN :startDate AND :endDate " +
            "GROUP BY p.prdc.name")
    List<Map<String, Object>> findTotalQuantityByPrdcNameBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
