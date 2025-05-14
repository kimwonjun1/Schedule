package com.capstone.schedule.repository;

import com.capstone.schedule.dto.ScheduleResponseDto;
import com.capstone.schedule.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<Schedule> findAllSchedules(String modificationDate, String name, Long writerId);

    List<Schedule> findPagedSchedules(int offset, int limit);

    Schedule findScheduleByIdOrElseThrow(Long id);

    int updateWork(Long id, String work, String password);

    int deleteSchedule(Long id);

}
