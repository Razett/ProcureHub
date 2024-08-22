package com.glkids.procurehub.repository;

import com.glkids.procurehub.dto.ProcurementDetailsDTO;
import com.glkids.procurehub.entity.PrdcMtrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrdcMtrlRepository extends JpaRepository<PrdcMtrl, Long> {

//    @Query("SELECT new com.glkids.procurehub.dto.ProcurementDetailsDTO(" +
//            "p.prcrno, p.reqdate, pp.prdcPlanNo, pp.startdate, pr.prdcno, pr.name, pp.quantity, " +
//            "m.mtrlno, m.name, m.standard, m.quantity, p.quantity, p.status, p.regdate, p.moddate) " +
//            "FROM Prcr p " +
//            "LEFT JOIN p.prdcPlan pp " +
//            "LEFT JOIN pp.prdc pr " +
//            "LEFT JOIN PrdcMtrl pm ON pm.prdc = pr " +
//            "LEFT JOIN pm.material m " +
//            "GROUP BY p.prcrno, p.reqdate, pp.prdcPlanNo, pp.startdate, pr.prdcno, pr.name, pp.quantity, " +
//            "m.mtrlno, m.name, m.standard, m.quantity, p.quantity, p.status, p.regdate, p.moddate")
//    List<ProcurementDetailsDTO> findProcurementDetails();

    @Query("select pm from PrdcMtrl pm where pm.material.mtrlno = :mtrlno")
    List<PrdcMtrl> findByMtrlno(@Param("mtrlno") Long mtrlno);
}
