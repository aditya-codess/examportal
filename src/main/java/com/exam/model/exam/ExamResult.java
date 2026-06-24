package com.exam.model.exam;

import com.exam.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "exam_result", uniqueConstraints = {
        @UniqueConstraint(name = "uq_exam_result_quiz_user", columnNames = {"quiz_id", "user_id"})
})
public class ExamResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long resultId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "quiz_id", nullable = false)
    @JsonIgnoreProperties({"questions", "category"})
    private Quiz exam;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"userRoles", "password"})
    private User student;

    private Double totalMarks;

    private Double obtainedMarks;

    private Double percentage;

    @Enumerated(EnumType.STRING)
    private ResultStatus status;

    private LocalDateTime submittedAt;

    private Integer rank;

    public ExamResult() {
    }

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public Quiz getExam() {
        return exam;
    }

    public void setExam(Quiz exam) {
        this.exam = exam;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
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

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
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
