package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.Export;
import com.glkids.procurehub.repository.search.ExportSearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ExportRepository extends JpaRepository<Export, Long>, QuerydslPredicateExecutor<Export>, ExportSearchRepository {

    long countByStatusIn(List<Integer> statuses);

}
