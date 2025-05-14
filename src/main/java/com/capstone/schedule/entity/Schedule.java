package com.capstone.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Schedule {
    private Long id;
    private String work;
    private String password;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private Long writerId;
    private String writerName;

    public Schedule(String work, String password, Long writerId) {
        this.work = work;
        this.password = password;
        this.writerId = writerId;
        this.creationDate = LocalDateTime.now();
        this.modificationDate = LocalDateTime.now();
    }

    public Schedule(Long id, String work, String password, LocalDateTime creationDate,
                    LocalDateTime modificationDate, Long writerId) {
        this.id = id;
        this.work = work;
        this.password = password;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.writerId = writerId;
    }

    public Schedule(Long id, String work, String password, LocalDateTime creationDate,
                    LocalDateTime modificationDate, Long writerId, String writerName) {
        this.id = id;
        this.work = work;
        this.password = password;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.writerId = writerId;
        this.writerName = writerName;
    }
}

