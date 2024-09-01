package com.glkids.procurehub.controller.rest;

import com.glkids.procurehub.entity.PrdcMtrl;
import com.glkids.procurehub.entity.QuotationMtrl;
import com.glkids.procurehub.service.ImportService;
import com.glkids.procurehub.service.PrdcPlanService;
import com.glkids.procurehub.service.PrdcService;
import com.glkids.procurehub.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/utils")
@RequiredArgsConstructor
public class UtilRestController {

    private final PrdcPlanService prdcPlanService;
    private final StatisticsService statisticsService;
    private final PrdcService prdcService;

    @GetMapping("/prdcPlanDoughnut")
    public ResponseEntity<List<Map<String, Object>>> getPrdcPlanDoughnut() {
        LocalDateTime startDate = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime endDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atTime(23, 59, 59);

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
    // 자재 목록을 가져오는 API 엔드포인트
    @GetMapping("/materials")
    @ResponseBody
    public List<PrdcMtrl> getMaterialsByProduct(@RequestParam("prdcno") Long prdcno) {
        return prdcService.getPrdcmtrlByPrdcno(prdcno);
    }

    @GetMapping("/materialList")
    @ResponseBody
    public List<QuotationMtrl> getMaterialsByProductNo(@RequestParam("prdcno") Long prdcno) {
        return prdcService.getMaterial(prdcService.getPrdcmtrlByPrdcno(prdcno));

    }
}
