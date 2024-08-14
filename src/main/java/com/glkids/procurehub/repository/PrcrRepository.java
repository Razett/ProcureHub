package com.glkids.procurehub.repository;

import com.glkids.procurehub.dto.ProcurementDetailsDTO;
import com.glkids.procurehub.entity.Prcr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrcrRepository extends JpaRepository<Prcr, Long> {

    @Query("SELECT new com.glkids.procurehub.dto.ProcurementDetailsDTO(" +
            "p.prcrno, p.reqdate, pp.prdcPlanNo, pp.startdate, pr.prdcno, pr.name, pp.quantity, " +
            "m.mtrlno, m.name, m.standard, m.quantity, p.quantity, p.status, p.regdate, p.moddate) " +
            "FROM Prcr p " +
            "LEFT JOIN p.prdcPlan pp " +
            "LEFT JOIN pp.prdc pr " +
            "LEFT JOIN PrdcMtrl pm ON pm.prdc = pr " +
            "LEFT JOIN pm.material m ")  // QuotationMtrl과 조인하여 leadTime 가져오기
    List<ProcurementDetailsDTO> findAllProcurements();
}
