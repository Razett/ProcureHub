package com.glkids.procurehub.repository;

import com.glkids.procurehub.dto.EmpCountDTO;
import com.glkids.procurehub.entity.Emp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmpRepository extends JpaRepository<Emp, Long> {
    Emp findByEmpno(Long empno);

    // 모든 사원 일일 건 수 나타내는 쿼리

    @Query("SELECT new com.glkids.procurehub.dto.EmpCountDTO(e.empno, e.name, " +
            "(SELECT COUNT(o) FROM Order o WHERE o.emp.empno = e.empno AND FUNCTION('DATE', o.orderdate) = CURRENT_DATE), " +
            "(SELECT COUNT(i) FROM Imports i WHERE i.approver.empno = e.empno AND FUNCTION('DATE', i.approvedate) = CURRENT_DATE), " +
            "(SELECT COUNT(ex) FROM Export ex WHERE ex.shipper.empno = e.empno AND FUNCTION('DATE', ex.shippeddate) = CURRENT_DATE), " +
            "(SELECT COUNT(q) FROM Quotation q WHERE q.emp.empno = e.empno AND FUNCTION('DATE', q.regdate) = CURRENT_DATE)) " +
            "FROM Emp e")
    List<EmpCountDTO> findEmpCounts();

    // 로그인 한 사람만 일일 건 수 나오는 쿼리
    @Query("SELECT new com.glkids.procurehub.dto.EmpCountDTO(e.empno, e.name, " +
            "(SELECT COUNT(o) FROM Order o WHERE o.emp.empno = :empNo AND FUNCTION('DATE', o.orderdate) = CURRENT_DATE), " +
            "(SELECT COUNT(i) FROM Imports i WHERE i.approver.empno = :empNo AND FUNCTION('DATE', i.approvedate) = CURRENT_DATE), " +
            "(SELECT COUNT(ex) FROM Export ex WHERE ex.shipper.empno = :empNo AND FUNCTION('DATE', ex.shippeddate) = CURRENT_DATE), " +
            "(SELECT COUNT(q) FROM Quotation q WHERE q.emp.empno = :empNo AND FUNCTION('DATE', q.regdate) = CURRENT_DATE)) " +
            "FROM Emp e WHERE e.empno = :empNo")
    EmpCountDTO getEmpCountsByCurrentUser(@Param("empNo") Long empNo);


}
