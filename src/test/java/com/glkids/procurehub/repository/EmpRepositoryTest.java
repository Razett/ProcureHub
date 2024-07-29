package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.Dept;
import com.glkids.procurehub.entity.Emp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class EmpRepositoryTest {

    @Autowired
    private EmpRepository empRepository;

    @Autowired
    private DeptRepository deptRepository;

    @Test
    public void initSave() {
        List<Dept> depts = deptRepository.findAll();
        empRepository.save(Emp.builder().empno(201758030L).name("정지원").pw("1234").dept(depts.get(0)).build());
    }
}
