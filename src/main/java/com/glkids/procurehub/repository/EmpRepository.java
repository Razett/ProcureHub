package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.Emp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpRepository extends JpaRepository<Emp, Long> {
    Emp findByEmpno(Long empno);
}
