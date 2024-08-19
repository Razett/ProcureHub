package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.ImportInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImportInspectionRepository extends JpaRepository<ImportInspection, Long> {

    @Query("select ins from ImportInspection ins where ins.imports.importno = :importno")
    List<ImportInspection> findByImportNo(@Param("importno") Long importno);
}
