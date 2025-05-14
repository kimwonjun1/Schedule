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
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Long writerId;
    private String writerName; // ← 추가

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.work = schedule.getWork();
        this.creationDate = schedule.getCreationDate();
        this.modificationDate = schedule.getModificationDate();
        this.writerId = schedule.getWriterId();
        this.writerName = schedule.getWriterName();
    }
}
