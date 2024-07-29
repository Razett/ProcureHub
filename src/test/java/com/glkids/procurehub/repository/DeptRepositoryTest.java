package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.Dept;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

@SpringBootTest
public class DeptRepositoryTest {

    @Autowired
    private DeptRepository deptRepository;

    @Test
    public void initSave() {
        deptRepository.save(Dept.builder().name("관리자").build());
        deptRepository.save(Dept.builder().name("인사과").build());
        deptRepository.save(Dept.builder().name("자재부서").build());
        deptRepository.save(Dept.builder().name("구매부서").build());
        deptRepository.save(Dept.builder().name("개발부서").build());
        deptRepository.save(Dept.builder().name("생산부서").build());
    }
}
