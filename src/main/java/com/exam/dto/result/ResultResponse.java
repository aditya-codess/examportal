package com.exam.dto.result;

import java.time.LocalDateTime;

public class ResultResponse {

    private Long resultId;
    private Long examId;
    private String examName;
    private Long studentId;
    private String studentUsername;
    private Double totalMarks;
    private Double obtainedMarks;
    private Double percentage;
    private String status;
    private LocalDateTime submittedAt;
    private Integer rank;

    public ResultResponse() {
    }

    public ResultResponse(Long resultId, Long examId, String examName, Long studentId, String studentUsername,
                           Double totalMarks, Double obtainedMarks, Double percentage, String status,
                           LocalDateTime submittedAt, Integer rank) {
        this.resultId = resultId;
        this.examId = examId;
        this.examName = examName;
        this.studentId = studentId;
        this.studentUsername = studentUsername;
        this.totalMarks = totalMarks;
        this.obtainedMarks = obtainedMarks;
        this.percentage = percentage;
        this.status = status;
        this.submittedAt = submittedAt;
        this.rank = rank;
    }

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
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

    public Double getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Double totalMarks) {
        this.totalMarks = totalMarks;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
