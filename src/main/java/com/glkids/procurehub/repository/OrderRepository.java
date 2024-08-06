package com.glkids.procurehub.repository;

import com.glkids.procurehub.dto.OrderDTO;
import com.glkids.procurehub.entity.Emp;
import com.glkids.procurehub.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Modifying
    @Query("UPDATE Order o SET o.emp = :emp, o.orderdate = :orderdate, o.status = :status WHERE o.orderno = :orderno")
    List<OrderDTO> orderExecute(@Param("orderno") Long orderno, @Param("emp") Emp emp, @Param("orderdate")LocalDateTime orderdate, @Param("status") Integer status);
}
