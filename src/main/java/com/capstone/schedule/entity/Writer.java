package com.capstone.schedule.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Writer {
    private Long id;                            // 작성자 식별자
    private String name;                        // 작성자명
    private String email;                       // 이메일
    private LocalDateTime creationDate;         // 작성일
    private LocalDateTime modificationDate;     // 수정일
}
