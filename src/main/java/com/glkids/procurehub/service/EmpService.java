package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.EmpCountDTO;
import com.glkids.procurehub.dto.EmpDTO;
import com.glkids.procurehub.entity.Emp;

import java.util.List;

public interface EmpService {

    List<String> list();

    List<EmpCountDTO> getEmpCounts();

    default EmpDTO empEntityToDTO(Emp emp){
        return EmpDTO.builder().name(emp.getName()).pw(emp.getPw())
                .empno(emp.getEmpno()).build();
    }


}
