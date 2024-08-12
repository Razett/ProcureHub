package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.MaterialGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MaterialGroupRepository extends JpaRepository<MaterialGroup, String>, QuerydslPredicateExecutor<MaterialGroup> {
    @Query("select mg from MaterialGroup mg order by mg.depth asc, mg.pGrpcode asc, mg.grpcode asc")
    List<MaterialGroup> findAllMaterialGroups();

    @Modifying
    @Query("Update MaterialGroup mg set mg.name = :name where mg.grpcode = :grpcode")
    Integer updateMaterialGroup(@Param("name") String name, @Param("grpcode") String grpcode);
}
