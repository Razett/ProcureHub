package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.PrdcPlan;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrdcPlanRepository extends JpaRepository<PrdcPlan, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE PrdcPlan p SET p.quantity = :quantity WHERE p.prdcPlanNo = :prdcPlanNo")
    int updateQuantityByPrdcPlanNo(@Param("quantity") Long quantity, @Param("prdcPlanNo") Long prdcPlanNo);

}
