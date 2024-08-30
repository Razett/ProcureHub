package com.glkids.procurehub.controller.rest;

import com.glkids.procurehub.service.ImportService;
import com.glkids.procurehub.service.PrdcPlanService;
import com.glkids.procurehub.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/utils")
@RequiredArgsConstructor
public class UtilRestController {

    private final PrdcPlanService prdcPlanService;
    private final StatisticsService statisticsService;

    @GetMapping("/prdcPlanDoughnut")
    public ResponseEntity<List<Map<String, Object>>> getPrdcPlanDoughnut() {
        LocalDateTime startDate = LocalDateTime.now().minusMonths(1); // 한 달 시작일 !
        LocalDateTime endDate = LocalDateTime.now(); //  현재 날짜 종료일 !

        List<Map<String, Object>> productionData = prdcPlanService.getTotalQuantityByPrdcNameForMonth(startDate, endDate);

        return ResponseEntity.ok(productionData);
    }

    @GetMapping("/monthlyProcessData")
    public ResponseEntity<List<Map<String, Long>>> getMonthlyProcessData() {
        List<Map<String, Long>> list = new ArrayList<>();
        list.add(statisticsService.getMonthOrderCounts());
        list.add(statisticsService.getMonthImportsCounts());
        list.add(statisticsService.getMonthExportsCounts());

        return ResponseEntity.ok(list);
    }
}
