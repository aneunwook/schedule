package org.example.schedule.controller;

import org.example.schedule.dto.ScheduleRequestDto;
import org.example.schedule.dto.ScheduleResponseDto;
import org.example.schedule.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto){
        return new ResponseEntity<>(scheduleService.createSchedule(scheduleRequestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules(){
        return new ResponseEntity<>(scheduleService.findAllSchedules(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ScheduleResponseDto>> searchSchedules(@RequestParam (required = false) String name, @RequestParam (required = false) LocalDate updatedAt){
        return new ResponseEntity<>(scheduleService.searchSchedules(name, updatedAt), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id){
        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateScheduleById(@PathVariable Long id, @RequestBody ScheduleRequestDto scheduleRequestDto){
        return new ResponseEntity<>(scheduleService.updateScheduleById(id, scheduleRequestDto), HttpStatus.OK);
    }

}
