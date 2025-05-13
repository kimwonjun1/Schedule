package com.capstone.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {
    // 속성
    private Long id;                            // 일정 식별자
    private String work;                        // 할 일
    private String password;                    // 비밀번호
    private LocalDateTime creationDate;         // 작성일
    private LocalDateTime modificationDate;     // 수정일
    private Long writerId;                      // 작성자 식별자

    // 생성자
    public Schedule(String work, String password, Long writerId) {
        this.work = work;
        this.password = password;
        this.writerId = writerId;
        this.creationDate = LocalDateTime.now();        // 현재 시간으로 초기화
        this.modificationDate = LocalDateTime.now();    // 현재 시간으로 초기화
    }
}
