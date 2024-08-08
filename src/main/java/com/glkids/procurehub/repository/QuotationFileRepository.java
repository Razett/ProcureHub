package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.MaterialFile;
import com.glkids.procurehub.entity.QuotationFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface QuotationFileRepository extends JpaRepository<QuotationFile, Long> , QuerydslPredicateExecutor<QuotationFile> {
}
