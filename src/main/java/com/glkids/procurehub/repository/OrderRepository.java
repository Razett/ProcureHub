package com.glkids.procurehub.repository;

import com.glkids.procurehub.dto.OrderDTO;
import com.glkids.procurehub.entity.Emp;
import com.glkids.procurehub.entity.Material;
import com.glkids.procurehub.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, QuerydslPredicateExecutor<Order> {

    @Modifying
    @Query("UPDATE Order o SET o.emp = :emp, o.orderdate = :orderdate, o.status = :status WHERE o.orderno = :orderno")
    void orderExecute(@Param("orderno") Long orderno, @Param("emp") Emp emp, @Param("orderdate") LocalDateTime orderdate, @Param("status") Integer status);

//    @Query("SELECT o FROM Order o JOIN FETCH o.quotationmtrl JOIN FETCH o.material WHERE o.orderno = :orderno")
//    Optional<Order> findByIdFetch(@Param("orderno") Long orderno);
}
