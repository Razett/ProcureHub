package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.Emp;
import com.glkids.procurehub.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void initSave() {
        orderRepository.save(Order.builder().emp(Emp.builder().empno(201758030L).build())
                .orderdate(LocalDateTime.now())
                .quantity(10L)
                .status(0).build());
    }
}
