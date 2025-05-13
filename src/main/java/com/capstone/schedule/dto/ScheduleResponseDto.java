package com.capstone.schedule.dto;

import com.capstone.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String work;
    private String password;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Long writerId;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.work = schedule.getWork();
        this.writerId = schedule.getWriterId();
        this.password = schedule.getPassword();
        this.creationDate = schedule.getCreationDate();
        this.modificationDate = schedule.getModificationDate();
    }
}
