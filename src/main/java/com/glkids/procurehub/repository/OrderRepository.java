package com.glkids.procurehub.repository;

import com.glkids.procurehub.dto.OrderDTO;
import com.glkids.procurehub.entity.Emp;
import com.glkids.procurehub.entity.Material;
import com.glkids.procurehub.entity.Order;
import com.glkids.procurehub.entity.QuotationMtrl;
import com.glkids.procurehub.repository.search.OrderSearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, QuerydslPredicateExecutor<Order>, OrderSearchRepository {

    // prcrno에 해당하는 Order를 조회하는 쿼리
    @Query("SELECT o FROM Order o WHERE o.prcr.prcrno = :prcrno")
    Order findByPrcr(@Param("prcrno") Long prcrno);

    // 발주 대기 현황에서 내용 수정 시 업데이트 되는 쿼리
    @Modifying
    @Query("update Order o set o.quotationmtrl = :quotationMtrl, o.quantity = :quantity, o.status = :status where o.orderno = :orderno")
    Integer updateOrder(@Param("quotationMtrl") QuotationMtrl quotationMtrl, @Param("quantity") Long quantity, @Param("status") Integer status, @Param("orderno") Long orderno);

    long countByStatusIn(List<Integer> statuses);

    @Modifying
    @Query("update Order o set o.status = :status where o.orderno = :orderno")
    void changeStatus(@Param("orderno") Long orderno, @Param("status") Integer status);

    //발주 대기 현황에서 발주 취소 시 삭제되는 쿼리
    @Modifying
    @Query("delete from Order o where o.orderno = :orderno")
    void orderDelete(@Param("orderno") Long orderno);

    @Query("select o from Order o where o.status > :status order by o.orderno desc")
    List<Order> findOrdersByStatus(@Param("status") Integer status);

    @Query("SELECT FUNCTION('DATE_FORMAT', o.orderdate, '%Y-%m') AS month, COUNT(o.orderdate) AS counts " +
            "FROM Order o " +
            "WHERE o.status > 5 " +
            "AND o.orderdate >= :startDate " +
            "GROUP BY FUNCTION('DATE_FORMAT', o.orderdate, '%Y-%m') ")
    List<Object[]> findMonthlyOrderCount(@Param("startDate") LocalDateTime startDate);


}
