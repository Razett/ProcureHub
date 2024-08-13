package com.glkids.procurehub.controller;

import com.glkids.procurehub.entity.Calendar;
import com.glkids.procurehub.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Calendar addCalendar(@RequestBody Calendar calendar) {
        return calendarService.saveCalendar(calendar);
    }

    @PostMapping("/update")  // 수정 시 사용할 API
    public Calendar updateCalendar(@RequestBody Calendar calendar) {
        // 기존 이벤트가 존재하는지 확인하고, 있다면 수정
        return calendarService.updateCalendar(calendar);
    }

    @PostMapping("/delete")  // 삭제 시 사용할 API
    public void deleteCalendar(@RequestBody Map<String, Long> requestData) {
        Long eventId = requestData.get("id");
        calendarService.deleteCalendar(eventId);
    }
}
