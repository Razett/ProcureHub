package com.glkids.procurehub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CalendarDTO { private Long id;

    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String backgroundColor;
    private String borderColor;
}
