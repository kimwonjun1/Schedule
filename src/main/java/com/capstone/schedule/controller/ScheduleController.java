package com.capstone.schedule.controller;

import com.capstone.schedule.dto.ScheduleRequestDto;
import com.capstone.schedule.dto.ScheduleResponseDto;
import com.capstone.schedule.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules") // prefix
public class ScheduleController {

    // ScheduleService의 의존성 주입
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 일정 생성 API
    // HttpMessage Body를 통해 json 데이터를 받아 RequestDto에 할당
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.CREATED); // Service Layer의 일정 생성 비즈니스 로직을 사용. (HttpMessage body를 통해 받은 json 데이터를 RequestDto에 할당하여 데이터 베이스에 추가)
    }                                                                                       // 생성 후 생성한 schedule의 정보(dto), 상태코드 OK(200)을 ResponseEntity에 추가하여 반환 (dto -> json)

    // 일정 전체 조회 API (writer_id를 통해 받아서 해당 writer의 모든 schedule을 조회, 작성자명이나 수정일을 통해서도 조회 가능)
    // Query Params를 통해 데이터를 선택적으로 받아 각각 modificationDate, name, writerId에 할당
    @GetMapping
    public List<ScheduleResponseDto> findAllSchedules(@RequestParam(required = false) String modificationDate, @RequestParam(required = false) String name, @RequestParam(required = false) Long writerId) {
        return scheduleService.findAllSchedules(modificationDate, name, writerId); // modificationDate, name, writerId 각각에 해당하는 모든 schedule을 할당한 responseDto를 반환 (Dto -> json)
    }

    // 일정 페이지 단위 조회 API
    // Query Params를 통해 page, size를 받아 Service Layer에 getPagedSchdules 메서드에 전달
    @GetMapping("/paged")
    public List<ScheduleResponseDto> getPagedSchedules(@RequestParam int page, @RequestParam int size) {
        return scheduleService.getPagedSchedules(page, size);
    }

    // 일정 단건 조회 API (schedule의 id를 사용하여 조회)
    // URL Path를 통해 데이터를 받아 매개변수 id에 할당
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {
        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK); // Service Layer의 일정 단건 조회 비즈니스 로직을 사용. (Path로 받은 id에 해당하는 schedule을 조회)
    }                                                                                       // id에 해당하는 schedule의 정보를 할당한 responseDto, 상태코드 OK(200)을 ResponseEntity에 추가하여 반환 (Dto -> json)

    //일정 수정 API (schedule의 id를 사용하여 수정, 비밀번호가 맞는 경우에)
    // URL Path를 통해 데이터를 받아 매개변수 id에 할당, HttpMessage Body를 통해 json 데이터를 받아 requestDto에 할당
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateWork(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        return new ResponseEntity<>(scheduleService.updateWork(id, requestDto.getWork(), requestDto.getPassword()), HttpStatus.OK); // Service Layer의 일정 수정 비즈니스 로직을 사용. (Path로 받은 id에 해당하는 schedule을 수정)
    }                                                                                                                               // requestDto의 work를 수정 후 할당한 responseDto, 상태코드 OK(200)을 ResponseEntity에 추가하여 반환 (Dto -> json)

    // 일정 삭제 API (schedule의 id를 사용해서 삭제)
    // URL Path를 통해 데이터를 받아 매개변수 id에 할당
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id); // Service Layer의 일정 삭제 비즈니스 로직을 사용. (Path로 받은 id에 해당하는 schedule을 삭제)
        return new ResponseEntity<>(HttpStatus.OK); // 삭제 후 상태코드 OK(200)을 ResponseEntity에 추가하여 반환
    }
}
