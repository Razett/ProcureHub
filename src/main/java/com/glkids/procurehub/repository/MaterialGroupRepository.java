package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.MaterialGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MaterialGroupRepository extends JpaRepository<MaterialGroup, String>, QuerydslPredicateExecutor<MaterialGroup> {

}
