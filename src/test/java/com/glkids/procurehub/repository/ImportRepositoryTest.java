package com.glkids.procurehub.repository;

import com.glkids.procurehub.dto.ImportDTO;
import com.glkids.procurehub.dto.StatisticsDTO;
import com.glkids.procurehub.entity.Emp;
import com.glkids.procurehub.entity.ImportInspection;
import com.glkids.procurehub.entity.Imports;
import com.glkids.procurehub.entity.Order;
import com.glkids.procurehub.service.ImportService;
import com.glkids.procurehub.service.StatisticsService;
import com.glkids.procurehub.status.InspectionStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class ImportRepositoryTest {

    @Autowired
    private ImportRepository importRepository;

    @Autowired
    private ImportInspectionRepository importInspectionRepository;

    @Autowired
    private ImportService importService;

    @Autowired
    private StatisticsService statisticsService;

    @Test
    public void initSave() {
        importRepository.save(Imports.builder().order(Order.builder().orderno(2L).build()).quantity(15L).status(0).build());
    }

    @Test
    public void initSave2() {
        importInspectionRepository.save(ImportInspection.builder().imports(Imports.builder().importno(1L).build()).emp(Emp.builder().empno(201758030L).build()).status(InspectionStatus.OK.ordinal()).content("testsetsetsets").dfcQuantity(0).duedate(LocalDateTime.now()).build());
        importInspectionRepository.save(ImportInspection.builder().imports(Imports.builder().importno(4L).build()).emp(Emp.builder().empno(201758030L).build()).status(InspectionStatus.DFC.ordinal()).content("testsetsetsets").dfcQuantity(3).duedate(LocalDateTime.now()).build());
        importInspectionRepository.save(ImportInspection.builder().imports(Imports.builder().importno(5L).build()).emp(Emp.builder().empno(201758030L).build()).status(InspectionStatus.NOT_YET.ordinal()).content("testsetsetsets").dfcQuantity(0).duedate(LocalDateTime.now()).build());
    }

    @Test
    public void getMonthImportInspectionByMaterialTest() {

        List<StatisticsDTO> list = statisticsService.getMonthImportInspectionByMaterial(1L);
        System.out.println(list);
    }
}
