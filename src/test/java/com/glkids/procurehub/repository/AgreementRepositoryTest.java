package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.Agreement;
import com.glkids.procurehub.entity.Contractor;
import com.glkids.procurehub.entity.Emp;
import com.glkids.procurehub.entity.Quotation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class AgreementRepositoryTest {

    @Autowired
    private AgreementRepository agreementRepository;

    @Test
    public void saveInit() {
        Emp emp = Emp.builder().empno(201758030L).build();
        agreementRepository.save(Agreement.builder().emp(emp)
                        .startdate(LocalDateTime.now())
                        .enddate(LocalDateTime.now())
                        .status(0)
                        .content("test_content")
                        .title("test_title")
                        .contractor(Contractor.builder().corno(123L).build())
                        .quotation(Quotation.builder().qtno(101L).build()).build());
    }
}
