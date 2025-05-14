package com.capstone.schedule.service;

import com.capstone.schedule.dto.ScheduleRequestDto;
import com.capstone.schedule.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {
    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);
    List<ScheduleResponseDto> findAllSchedules(String modificationDate, String name, Long writerId);
    List<ScheduleResponseDto> getPagedSchedules(int page, int size);
    ScheduleResponseDto findScheduleById(Long id);
    ScheduleResponseDto updateWork(Long id, String work, String password);
    void deleteSchedule(Long id);
}
