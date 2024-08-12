package com.glkids.procurehub.service;

import com.glkids.procurehub.entity.Calendar;
import com.glkids.procurehub.repository.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarService {

    @Autowired
    private CalendarRepository calendarRepository;

    public List<Calendar> getAllCalendar() {
        return calendarRepository.findAll();
    }

    public Calendar saveCalendar(Calendar calendar) {
        return calendarRepository.save(calendar);
    }
}
