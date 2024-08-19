package com.glkids.procurehub.controller;

import com.glkids.procurehub.dto.CalendarDTO;
import com.glkids.procurehub.entity.Calendar;
import com.glkids.procurehub.service.CalendarService;
import com.glkids.procurehub.service.PrdcPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/Calendar")
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

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

    @PostMapping("/update")
    public Calendar updateCalendar(@RequestBody Calendar calendar) {
        // 여기서 calendar 객체는 backgroundColor와 borderColor 필드를 무시하고 매핑됩니다.
        return calendarService.updateCalendar(calendar);
    }

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
}
