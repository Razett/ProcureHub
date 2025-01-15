package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
public class PrcrRepositoryTest {

    @Autowired
    private PrcrRepository prcrRepository;

    @Autowired
    private PrcrRepository prcrRepository2;

    @Autowired
    private PrdcPlanRepository prdcPlanRepository;

    @Autowired
    private PrdcMtrlRepository prdcMtrlRepository;


    @Test
    public void findAll() {
        for(int i = 1 ; i <= 15 ; i++) {
            long randomQuantity = ThreadLocalRandom.current().nextLong(1, 10); // 1에서 10 사이의 난수
            long randomMtrlno = ThreadLocalRandom.current().nextLong(1, 100); // 1에서 100 사이의 난수
            long randomPrdcPlanNo = ThreadLocalRandom.current().nextLong(1, 100); // 1에서 100 사이의 난수
            int randomStatus = ThreadLocalRandom.current().nextInt(1, 5); // 1에서 5 사이의 정수 난수

            LocalDateTime randomReqdate = LocalDateTime.now().plusDays(ThreadLocalRandom.current().nextLong(1, 31));

            prcrRepository.save(Prcr.builder()
                    .quantity(randomQuantity)
                    .reqdate(randomReqdate)
                    .status(randomStatus)
                    .emp(Emp.builder().empno(201723058L).build())
                    .material(Material.builder().mtrlno(randomMtrlno).build())
                    .prdcPlan(PrdcPlan.builder().prdcPlanNo(randomPrdcPlanNo).build())
                    .build());
        }
    }
    @Test
    public void prdcMtrlTest(){
        prdcMtrlRepository.save(PrdcMtrl.builder().quantity(3).material(Material.builder().mtrlno(3L).build()).prdc(Prdc.builder().prdcno(6L).build()).build());
        prdcMtrlRepository.save(PrdcMtrl.builder().quantity(4).material(Material.builder().mtrlno(5L).build()).prdc(Prdc.builder().prdcno(6L).build()).build());
        prdcMtrlRepository.save(PrdcMtrl.builder().quantity(10).material(Material.builder().mtrlno(7L).build()).prdc(Prdc.builder().prdcno(6L).build()).build());
    }



    @Test
    public void prcrPlanTest(){
        prdcPlanRepository.save(PrdcPlan.builder().enddate(LocalDateTime.now()).startdate(LocalDateTime.now()).quantity(3L).prdc(Prdc.builder().prdcno(8L).build()).build()


        );
    }
}
