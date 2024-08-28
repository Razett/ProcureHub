package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.EmpCountDTO;
import com.glkids.procurehub.dto.EmpDTO;
import com.glkids.procurehub.dto.EmpMonthDTO;
import com.glkids.procurehub.entity.Emp;

import java.util.List;

public interface EmpService {

    List<String> list();

    List<EmpCountDTO> getEmpCounts();

    // 로그인한 사람만 나오게 일일 건
    EmpCountDTO getCurrentUserEmpCounts();

    // 모든 사원 한달건
    List<EmpMonthDTO> getMonthCounts();

    default EmpDTO empEntityToDTO(Emp emp){
        return EmpDTO.builder().name(emp.getName()).pw(emp.getPw())
                .empno(emp.getEmpno()).build();
    }


}
