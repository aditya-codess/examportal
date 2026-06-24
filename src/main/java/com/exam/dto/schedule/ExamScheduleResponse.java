package com.exam.dto.schedule;

import java.time.LocalDateTime;

public class ExamScheduleResponse {

    private Long scheduleId;
    private Long examId;
    private String examName;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Long durationMinutes;
    private String status;

    public ExamScheduleResponse() {
    }

    public ExamScheduleResponse(Long scheduleId, Long examId, String examName, LocalDateTime startDateTime,
                                 LocalDateTime endDateTime, Long durationMinutes, String status) {
        this.scheduleId = scheduleId;
        this.examId = examId;
        this.examName = examName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.durationMinutes = durationMinutes;
        this.status = status;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Long getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Long durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
