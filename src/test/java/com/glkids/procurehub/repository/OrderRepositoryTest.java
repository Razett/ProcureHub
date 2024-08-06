package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.*;
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
        Quotation quotation = Quotation.builder().qtno(32L).build();
        QuotationMtrl quotationMtrl = QuotationMtrl.builder().quotation(quotation).qtmtno(7L).build();
        orderRepository.save(Order.builder().emp(Emp.builder().build())
                        .material(Material.builder().mtrlno(1L).build())
                        .quotationmtrl(quotationMtrl)
                        .quantity(10L)
                        .status(0).build());
    }
}
