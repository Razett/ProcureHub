package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.QuotationMtrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface QuotationMtrlRepository extends JpaRepository<QuotationMtrl, Long> , QuerydslPredicateExecutor<QuotationMtrl> {
}
