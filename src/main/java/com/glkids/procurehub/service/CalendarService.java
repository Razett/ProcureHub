package com.glkids.procurehub.service;

import com.glkids.procurehub.entity.Calendar;
import com.glkids.procurehub.repository.CalendarRepository;
import jakarta.transaction.Transactional;
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

    public Calendar updateCalendar(Calendar calendar) {
        // 존재하는지 확인 후 수정
        Calendar existingCalendar = calendarRepository.findById(calendar.getId())
                .orElseThrow(() -> new RuntimeException("이벤트를 찾을 수 없습니다."));
        existingCalendar.setTitle(calendar.getTitle());
        existingCalendar.setStartDate(calendar.getStartDate());
        existingCalendar.setEndDate(calendar.getEndDate());
        return calendarRepository.save(existingCalendar);
    }

    public void deleteCalendar(Long id) {
        // 존재하는지 확인 후 삭제
        Calendar existingCalendar = calendarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("이벤트를 찾을 수 없습니다."));
        calendarRepository.delete(existingCalendar);
    }
}
