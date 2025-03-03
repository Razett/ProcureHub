package com.glkids.procurehub.controller.rest;

import com.glkids.procurehub.dto.CalendarDTO;
import com.glkids.procurehub.dto.PrdcDTO;
import com.glkids.procurehub.dto.PrdcPlanDTO;
import com.glkids.procurehub.entity.Calendar;
import com.glkids.procurehub.entity.Prdc;
import com.glkids.procurehub.entity.PrdcMtrl;
import com.glkids.procurehub.service.CalendarService;
import com.glkids.procurehub.service.PrdcPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/calendar")
public class CalendarRestController {

    @Autowired
    private CalendarService calendarService;
    @Autowired
    private PrdcPlanService prdcPlanService;

    @Deprecated
    @GetMapping("/read")
    public List<Map<String, Object>> getEvents() {
        return calendarService.getAllCalendar().stream().map(event -> {
            Map<String, Object> eventData = new HashMap<>();
            eventData.put(("id"), event.getId());
            eventData.put("title", event.getTitle());
            eventData.put("start", event.getStartDate());
            eventData.put("end", event.getEndDate());
            return eventData;
        }).collect(Collectors.toList());
    }
    @Deprecated
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addEvent(@RequestBody CalendarDTO calendarDTO) {
        Calendar calendar = new Calendar();
        calendar.setTitle(calendarDTO.getTitle());
        calendar.setStartDate(calendarDTO.getStartDate());
        calendar.setEndDate(calendarDTO.getEndDate());
        calendarService.saveCalendar(calendar);

        // 서버에서 유효한 JSON 객체를 반환
        Map<String, Object> response = new HashMap<>();
        response.put("id", calendar.getId());
        response.put("message", "Event added successfully");

        return ResponseEntity.ok(response);
    }
    @Deprecated
    @PostMapping("/update")
    public Calendar updateCalendar(@RequestBody Calendar calendar) {
        // 여기서 calendar 객체는 backgroundColor와 borderColor 필드를 무시하고 매핑됩니다.
        return calendarService.updateCalendar(calendar);
    }
    @Deprecated
    @PostMapping("/delete")  // 삭제 시 사용할 API
    public void deleteCalendar(@RequestBody Map<String, Long> requestData) {
        Long eventId = requestData.get("id");
        calendarService.deleteCalendar(eventId);
    }


    @GetMapping("/prdcPlans")
    public List<Map<String, Object>> getPrdcPlans() {
        return calendarService.getAllPrdcPlan().stream().map(event -> {
            Map<String, Object> plan = new HashMap<>();
            plan.put(("planNo"), event.getPrdcPlanNo());
            plan.put("start", event.getStartdate());
            plan.put("end", event.getEnddate());
            plan.put("quantity+", event.getQuantity());
            plan.put("prdcName", event.getPrdc().getName());
            return plan;
        }).collect(Collectors.toList());
    }
    @PostMapping("/addPrdcPlan")
    public ResponseEntity<String> addPrdcPlan(@RequestBody PrdcPlanDTO prdcPlanDTO) {
        prdcPlanService.createPrdcPlan(prdcPlanDTO);
        return ResponseEntity.ok("생산 계획이 추가되었습니다.");
    }
    @GetMapping("/all")
    public ResponseEntity<List<PrdcDTO>> getAllPrdc() {
        List<Prdc> prdcList = prdcPlanService.getAllprdc();
        List<PrdcDTO> prdcDTOList = prdcList.stream().map(prdc -> {
            PrdcDTO prdcDTO = new PrdcDTO();
            prdcDTO.setPrdcno(prdc.getPrdcno());
            prdcDTO.setName(prdc.getName());
            return prdcDTO;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(prdcDTOList);
    }

    @GetMapping("/{prdcno}")
    public ResponseEntity<PrdcDTO> getPrdcByNo(@PathVariable Long prdcno) {
            Prdc prdc = prdcPlanService.getPrdcByNo(prdcno);
        // 각 PrdcMtrl의 수량을 리스트로 가져오기
        List<Integer> quantities = prdc.getPrdcmtrls().stream()
                .map(PrdcMtrl::getQuantity)
                .collect(Collectors.toList());

        List<String> materialNames = prdc.getPrdcmtrls().stream()
                .map(prdcMtrl -> prdcMtrl.getMaterial().getName() )
                .collect(Collectors.toList());

            PrdcDTO prdcDTO = new PrdcDTO();
            prdcDTO.setPrdcno(prdc.getPrdcno());
            prdcDTO.setName(prdc.getName());
            prdcDTO.setQuantity(quantities);
            prdcDTO.setMaterialNames(materialNames);
            return ResponseEntity.ok(prdcDTO);
        }

    @PostMapping("/updatePrdcPlan")
    public Long updatePrdcPlan(@RequestBody PrdcPlanDTO prdcPlanDTO) {
        return prdcPlanService.updatePrdcPlan(prdcPlanDTO);
    }
}
