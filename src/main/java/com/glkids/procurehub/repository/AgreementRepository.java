package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AgreementRepository extends JpaRepository<Agreement, Long>, QuerydslPredicateExecutor<Agreement> {

    @Modifying
    @Query("UPDATE Agreement a SET a.status = :status WHERE a.grmno = :grmno")
    void changeStatus(@Param("grmno") Long grmno, @Param("status") Integer status);
}
