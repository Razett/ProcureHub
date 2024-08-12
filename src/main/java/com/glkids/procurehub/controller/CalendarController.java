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

    @GetMapping
    public List<Map<String, Object>> getEvents() {
        return calendarService.getAllCalendar().stream().map(event -> {
            Map<String, Object> eventData = new HashMap<>();
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
}
