package com.exam.repo;

import com.exam.model.User;
import com.exam.model.exam.ExamResult;
import com.exam.model.exam.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {

    Optional<ExamResult> findByExamAndStudent(Quiz exam, User student);

    List<ExamResult> findByStudent(User student);

    List<ExamResult> findByExam(Quiz exam);


    List<ExamResult> findByExamOrderByObtainedMarksDescPercentageDescSubmittedAtAsc(Quiz exam);
}
