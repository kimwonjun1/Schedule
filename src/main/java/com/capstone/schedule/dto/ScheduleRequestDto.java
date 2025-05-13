package com.capstone.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleRequestDto {
    private String work;
    private Long writerId;
    private String password;
}
