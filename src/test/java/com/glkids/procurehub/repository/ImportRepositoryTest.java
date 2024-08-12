package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.Import;
import com.glkids.procurehub.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ImportRepositoryTest {

    @Autowired
    private ImportRepository importRepository;

    @Test
    public void initSave() {
        importRepository.save(Import.builder().order(Order.builder().orderno(2L).build()).quantity(15L).status(0).build());
    }
}
