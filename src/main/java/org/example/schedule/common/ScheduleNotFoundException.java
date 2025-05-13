package org.example.schedule.common;

public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException(String message) {
        super("해당 게시물이 없습니다 " + message);
    }
}
