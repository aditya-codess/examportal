package com.exam.dto.merit;

import java.time.LocalDateTime;

/**
 * A single ranked entry within an exam's merit list.
 */
public class MeritListEntryResponse {

    private Integer rank;
    private Long studentId;
    private String studentUsername;
    private Double obtainedMarks;
    private Double percentage;
    private LocalDateTime submittedAt;

    public MeritListEntryResponse() {
    }

    public MeritListEntryResponse(Integer rank, Long studentId, String studentUsername, Double obtainedMarks,
                                   Double percentage, LocalDateTime submittedAt) {
        this.rank = rank;
        this.studentId = studentId;
        this.studentUsername = studentUsername;
        this.obtainedMarks = obtainedMarks;
        this.percentage = percentage;
        this.submittedAt = submittedAt;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }

    public Double getObtainedMarks() {
        return obtainedMarks;
    }

    public void setObtainedMarks(Double obtainedMarks) {
        this.obtainedMarks = obtainedMarks;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}
