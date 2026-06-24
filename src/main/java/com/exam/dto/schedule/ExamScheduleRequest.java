package com.exam.dto.schedule;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * Request payload for creating or updating an exam schedule.
 * "examId" refers to the id of the existing Quiz (exam) entity.
 */
public class ExamScheduleRequest {

    @NotNull(message = "examId is required")
    private Long examId;

    private String examName;

    @NotNull(message = "startDateTime is required")
    private LocalDateTime startDateTime;

    @NotNull(message = "endDateTime is required")
    private LocalDateTime endDateTime;

    /**
     * Optional. If omitted, duration is derived from start/end date-time.
     */
    private Long durationMinutes;

    /**
     * Optional. Defaults to SCHEDULED when omitted on creation.
     */
    private String status;

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
