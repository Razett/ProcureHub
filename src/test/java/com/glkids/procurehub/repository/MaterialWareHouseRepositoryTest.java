package com.glkids.procurehub.repository;

import com.glkids.procurehub.entity.MaterialWarehouse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MaterialWareHouseRepositoryTest {

    @Autowired
    private MaterialWarehouseRepository materialWareHouseRepository;

    @Test
    public void initSave() {
        materialWareHouseRepository.save(MaterialWarehouse.builder().wrhscode("A-S1-01").name("부품집").build());
        materialWareHouseRepository.save(MaterialWarehouse.builder().wrhscode("A-S1-02").name("전원공급집").build());
        materialWareHouseRepository.save(MaterialWarehouse.builder().wrhscode("A-S1-03").name("동력계집").build());
        materialWareHouseRepository.save(MaterialWarehouse.builder().wrhscode("A-S2-01").name("제어장치집").build());
        materialWareHouseRepository.save(MaterialWarehouse.builder().wrhscode("A-S2-02").name("안테나집").build());
        materialWareHouseRepository.save(MaterialWarehouse.builder().wrhscode("A-S3-01").name("카메라집").build());
        materialWareHouseRepository.save(MaterialWarehouse.builder().wrhscode("A-S3-02").name("배터리집").build());
        materialWareHouseRepository.save(MaterialWarehouse.builder().wrhscode("A-S3-03").name("ESC집").build());
        materialWareHouseRepository.save(MaterialWarehouse.builder().wrhscode("A-S3-04").name("메인보드집").build());
        materialWareHouseRepository.save(MaterialWarehouse.builder().wrhscode("A-S3-05").name("나사집").build());
        materialWareHouseRepository.save(MaterialWarehouse.builder().wrhscode("B-S1-01").name("프로펠러집").build());
        materialWareHouseRepository.save(MaterialWarehouse.builder().wrhscode("B-S1-02").name("GPS집").build());
        materialWareHouseRepository.save(MaterialWarehouse.builder().wrhscode("B-S1-03").name("수신기집").build());
        materialWareHouseRepository.save(MaterialWarehouse.builder().wrhscode("B-S1-04").name("짐벌집").build());
        materialWareHouseRepository.save(MaterialWarehouse.builder().wrhscode("B-S1-05").name("LED집").build());
        materialWareHouseRepository.save(MaterialWarehouse.builder().wrhscode("B-S1-06").name("랜딩모듈집").build());
        materialWareHouseRepository.save(MaterialWarehouse.builder().wrhscode("B-S2-01").name("텔레메트리집").build());
        materialWareHouseRepository.save(MaterialWarehouse.builder().wrhscode("B-S2-02").name("오디오집").build());
        materialWareHouseRepository.save(MaterialWarehouse.builder().wrhscode("B-S2-03").name("CAS집").build());
    }
}
