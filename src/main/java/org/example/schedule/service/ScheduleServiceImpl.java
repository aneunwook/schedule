package org.example.schedule.service;

import org.example.schedule.common.PasswordDoesNotMatch;
import org.example.schedule.common.ScheduleNotFoundException;
import org.example.schedule.dto.AuthorResponseDto;
import org.example.schedule.dto.Paging;
import org.example.schedule.dto.ScheduleRequestDto;
import org.example.schedule.dto.ScheduleResponseDto;
import org.example.schedule.entity.Author;
import org.example.schedule.entity.Schedule;
import org.example.schedule.repository.AuthorRepository;
import org.example.schedule.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;
    private final AuthorRepository authorRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, AuthorRepository authorRepository) {
        this.scheduleRepository = scheduleRepository;
        this.authorRepository = authorRepository;
    }

    /**
     * 일정 생성하는 메서드
     */
    @Override
    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = new Schedule(scheduleRequestDto.getName(), scheduleRequestDto.getAuthorId(), scheduleRequestDto.getPassword(), scheduleRequestDto.getContents());

        if (scheduleRequestDto.getName() == null || scheduleRequestDto.getContents() == null || scheduleRequestDto.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The name and contents and password are required values.");
        }

        Schedule save = scheduleRepository.save(schedule);
        Author author = authorRepository.findById(scheduleRequestDto.getAuthorId());

        return new ScheduleResponseDto(save, author);
    }

    /**
     * 페이징을 이용하여 전체 일정 조회
     */
    @Override
    public List<ScheduleResponseDto> findAllSchedules(int page, int size) {
        Paging paging = new Paging(page, size);

        return scheduleRepository.findAllSchedules(paging);
    }

    /**
     * ID로 일정 상세 조회
     */
    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        return scheduleRepository.findScheduleById(id);
    }

    /**
     * 검색 조건(name, updatedAt, authorId)에 따라 일정 목록 조회
     */
    @Override
    public List<ScheduleResponseDto> searchSchedules(String name, LocalDate updatedAt, Long authorId) {
        return scheduleRepository.findSchedulesByParams(name, updatedAt, authorId);
    }

    /**
     * 일정 수정하는 메서드
     */
    @Transactional
    @Override
    public ScheduleResponseDto updateScheduleById(Long id, ScheduleRequestDto scheduleRequestDto) {

        ScheduleResponseDto scheduleById = scheduleRepository.findScheduleById(id);

        if(scheduleById.getId() == null){
            throw new ScheduleNotFoundException("id = " + id);
        }

        if(!scheduleById.getPassword().equals(scheduleRequestDto.getPassword())){
            throw new PasswordDoesNotMatch("비밀번호가 틀립니다");
        }

        int updated = scheduleRepository.updateSchedule(id, scheduleRequestDto);

        if (updated == 0) {
            throw new ScheduleNotFoundException("id = " + scheduleById);
        }

        return scheduleRepository.findScheduleById(id);
    }

    /**
     * 알정 삭제
     */
    @Override
    public void deleteSchedule(Long id, String password) {
        ScheduleResponseDto scheduleById = scheduleRepository.findScheduleById(id);

        if(scheduleById == null){
            throw new ScheduleNotFoundException("id = " + id);
        }

        if(!password.equals(scheduleById.getPassword())){
            throw new PasswordDoesNotMatch("비밀번호가 틀립니다");
        }

        int deleted = scheduleRepository.deleteSchedule(id, password);

        if (deleted == 0){
            throw new ScheduleNotFoundException("id = " + id);

        }
    }
}
