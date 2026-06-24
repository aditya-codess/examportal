package com.exam.model.exam;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "exam_schedule")
public class ExamSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long scheduleId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "quiz_id", nullable = false, unique = true)
    private Quiz exam;


    private String examName;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;


    private Long durationMinutes;

    @Enumerated(EnumType.STRING)
    private ExamStatus status = ExamStatus.SCHEDULED;

    public ExamSchedule() {
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Quiz getExam() {
        return exam;
    }

    public void setExam(Quiz exam) {
        this.exam = exam;
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

    public ExamStatus getStatus() {
        return status;
    }

    public void setStatus(ExamStatus status) {
        this.status = status;
    }
}
