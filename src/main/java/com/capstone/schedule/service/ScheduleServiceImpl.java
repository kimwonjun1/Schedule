package com.capstone.schedule.service;

import com.capstone.schedule.dto.ScheduleRequestDto;
import com.capstone.schedule.dto.ScheduleResponseDto;
import com.capstone.schedule.entity.Schedule;
import com.capstone.schedule.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    // ScheduleRepository의 의존성 주입
    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // 일정 생성 비즈니스 로직
    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) { 
        Schedule schedule = new Schedule(dto.getWork(), dto.getPassword(), dto.getWriterId()); // 매개변수로 받은 RequestDto에서 work, password, writerId를 통해 schedule 인스턴스 생성

        return scheduleRepository.saveSchedule(schedule);                                       // schedule을 Repository의 saveSchedule 메서드의 매개변수로 활용
    }

    // 일정 전체 조회 비즈니스 로직
    @Override
    public List<ScheduleResponseDto> findAllSchedules(String modificationDate, String name, Long writerId) {
        List<Schedule> schedules = scheduleRepository.findAllSchedules(modificationDate, name, writerId); // Query Params(modificationDate, name, writerId)를 통해 schedule 리스트 조회
        List<ScheduleResponseDto> responseList = new ArrayList<>();

        // Schedule 리스트를 ResponseDto 리스트로 변환
        for (int i = 0; i < schedules.size(); i++) {
            Schedule schedule = schedules.get(i);
            ScheduleResponseDto dto = new ScheduleResponseDto(schedule);
            responseList.add(dto);
        }

        return responseList; // 변환된 리스트 반환
    }

    // 일정 페이지 단위 조회 비즈니스 로직
    @Override
    public List<ScheduleResponseDto> getPagedSchedules(int page, int size) {
        int offset = page * size;

        List<Schedule> pagedSchedules = scheduleRepository.findPagedSchedules(offset, size);
        List<ScheduleResponseDto> responseList = new ArrayList<>();

        // Schedule 리스트를 ResponseDto 리스트로 변환
        for (int i = 0; i < pagedSchedules.size(); i++) {
            Schedule schedule = pagedSchedules.get(i);
            ScheduleResponseDto dto = new ScheduleResponseDto(schedule);
            responseList.add(dto);
        }
        return responseList; // 변환된 리스트 반환
    }

    // 일정 단건 조회 비즈니스 로직
    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        return new ScheduleResponseDto(scheduleRepository.findScheduleByIdOrElseThrow(id)); // id값을 매개변수로 Repository의 findScheduleByIdOrElseThrow(해당하는 id가 없는 경우 에러 처리) 메서드 호출
    }

    // 일정 수정 비즈니스 로직
    @Transactional
    @Override
    public ScheduleResponseDto updateWork(Long id, String work, String password) {
        int updated = scheduleRepository.updateWork(id, work, password); // 비밀번호가 일치하는 경우에만 work 필드를 수정

        if (updated == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No schedule updated."); // 수정된 행이 없으면 예외 발생
        }

        return new ScheduleResponseDto(scheduleRepository.findScheduleByIdOrElseThrow(id));  // 수정된 엔티티를 조회하여 ResponseDto로 변환 후 반환
    }

    // 일정 삭제 비즈니스 로직
    @Override
    public void deleteSchedule(Long id) {
        int deleted = scheduleRepository.deleteSchedule(id); // id에 해당하는 Schedule을 삭제

        if (deleted == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found."); // 삭제된 행이 없으면 예외 발생
        }
    }
}
