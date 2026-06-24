package com.exam.dto.result;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Request payload submitted when a student finishes an exam.
 * "answers" maps questionId -> the option/text the student selected.
 */
public class ResultSubmissionRequest {

    @NotNull(message = "examId is required")
    private Long examId;

    @NotNull(message = "studentId is required")
    private Long studentId;

    @NotEmpty(message = "answers must not be empty")
    private Map<Long, String> answers;

    /**
     * Optional. Defaults to the current server time when omitted.
     */
    private LocalDateTime submittedAt;

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Map<Long, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Long, String> answers) {
        this.answers = answers;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}
