package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.MaterialFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MaterialFileRepository extends JpaRepository<MaterialFile, Long>, QuerydslPredicateExecutor<MaterialFile> {
}
