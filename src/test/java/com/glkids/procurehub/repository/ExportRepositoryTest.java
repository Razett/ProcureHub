package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class ExportRepositoryTest {


    @Autowired
    private ExportRepository exportRepository;


    @Test
    public void saveExportTest() {
        // 생성된 Emp 객체의 ID를 사용하여 Export 엔티티 생성
        Export export = Export.builder()
                .quantity(100L)
                .duedate(LocalDateTime.now().plusDays(7))
                .prcr(Prcr.builder().prcrno(33L).build())
                .status(1)
                .emp(Emp.builder().empno(201758030L).build())
                .build();

        exportRepository.save(export);

        // 추가적인 테스트 로직
    }
}
