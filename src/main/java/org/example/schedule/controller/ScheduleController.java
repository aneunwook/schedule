package org.example.schedule.controller;

import org.example.schedule.dto.ScheduleRequestDto;
import org.example.schedule.dto.ScheduleResponseDto;
import org.example.schedule.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 일정 관련 요청을 처리하는 REST 컨트롤러
 */
@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    /**
     * 새로운 일정 생성
     *
     * @param scheduleRequestDto 일정 생성에 관한 정보
     * @return 일정 생성 성공시 201 응답
     */
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto){
        return new ResponseEntity<>(scheduleService.createSchedule(scheduleRequestDto), HttpStatus.CREATED);
    }

    /**
     * 모든 일정을 페이징하여 조회
     *
     * @param page 조회할 페이지 번호
     * @param size 한 페이지 당 일정 수
     * @return 일정 리스트와 200 응답
     */
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size){
        return new ResponseEntity<>(scheduleService.findAllSchedules(page, size), HttpStatus.OK);
    }

    /**
     * 일정 검색 기능 - 이름, 수정일, 작성자 ID를 기준
     *
     * @param name 일정 이름으로 조회
     * @param updatedAt 일정 수정 날짜로 조회
     * @param authorId 작성자 ID로 조회
     * @return 검색된 일정 리스트와 200 응답
     */
    @GetMapping("/search")
    public ResponseEntity<List<ScheduleResponseDto>> searchSchedules(@RequestParam (required = false) String name,
                                                                     @RequestParam (required = false) LocalDate updatedAt,
                                                                     @RequestParam (required = false) Long authorId){
        return new ResponseEntity<>(scheduleService.searchSchedules(name, updatedAt, authorId), HttpStatus.OK);
    }

    /**
     * 특정 일정 조회
     *
     * @param id 조회할 일정의 ID
     * @return 해당 일정과 200 응답
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id){
        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    /**
     * 일정 내용 수정
     *
     * @param id 수정할 해당 id
     * @param scheduleRequestDto 수정할 정보
     * @return 수정된 일정 정보와 200 응답
     */

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateScheduleById(@PathVariable Long id, @RequestBody ScheduleRequestDto scheduleRequestDto) {
        return new ResponseEntity<>(scheduleService.updateScheduleById(id, scheduleRequestDto), HttpStatus.OK);
    }

    /**
     * 일정 삭제
     *
     * @param id 삭제할 해당 id
     * @param scheduleRequestDto 비밀번호 추출
     * @return 삭제 후 200 응답
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto scheduleRequestDto){

        scheduleService.deleteSchedule(id, scheduleRequestDto.getPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
