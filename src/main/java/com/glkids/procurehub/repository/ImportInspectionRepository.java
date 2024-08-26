package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.ImportInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImportInspectionRepository extends JpaRepository<ImportInspection, Long> {

    @Query("select ins from ImportInspection ins where ins.imports.importno = :importno")
    List<ImportInspection> findByImportNo(@Param("importno") Long importno);

    @Query(value = "SELECT DATE_FORMAT(i.approvedate, '%Y-%m') AS month, SUM(i.quantity) AS quantity, SUM(ins.dfc_quantity) AS dfc " +
            "FROM import_inspection ins " +
            "LEFT JOIN imports i ON ins.imports_importno = i.importno " +
            "LEFT JOIN `order` o ON i.order_orderno = o.orderno " +
            "WHERE o.material_mtrlno = :mtrlno " +
            "AND ins.status > 0 AND ins.status < 4 " +
            "AND i.approvedate >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH) " +
            "GROUP BY DATE_FORMAT(i.approvedate, '%Y-%m') " +
            "ORDER BY month DESC", nativeQuery = true)
    List<Object[]> findMonthlyInspectionData(@Param("mtrlno") Long mtrlno);

}
