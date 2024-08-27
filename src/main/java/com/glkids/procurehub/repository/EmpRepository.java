package com.glkids.procurehub.repository;

import com.glkids.procurehub.dto.EmpCountDTO;
import com.glkids.procurehub.entity.Emp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmpRepository extends JpaRepository<Emp, Long> {
    Emp findByEmpno(Long empno);

    @Query("SELECT new com.glkids.procurehub.dto.EmpCountDTO(e.empno, e.name, " +
            "(SELECT COUNT(i) FROM Imports i WHERE i.approver.empno = e.empno AND FUNCTION('DATE', i.approvedate) = CURRENT_DATE), " +
            "(SELECT COUNT(ex) FROM Export ex WHERE ex.shipper.empno = e.empno AND FUNCTION('DATE', ex.shippeddate) = CURRENT_DATE)) " +
            "FROM Emp e")
    List<EmpCountDTO> findEmpCounts();


}
