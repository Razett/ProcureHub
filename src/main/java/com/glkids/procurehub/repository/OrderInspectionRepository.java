package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.OrderInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderInspectionRepository extends JpaRepository<OrderInspection, Long> {

    @Query("select ins from OrderInspection ins where ins.order.orderno = :orderno order by ins.nspcno desc")
    List<OrderInspection> readOrderInspectionByOrderno(@Param("orderno") Long orderno);
}
