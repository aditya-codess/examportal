package com.exam.repo;

import com.exam.model.exam.ExamSchedule;
import com.exam.model.exam.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ExamScheduleRepository extends JpaRepository<ExamSchedule, Long> {

    Optional<ExamSchedule> findByExam(Quiz exam);


    @Query("SELECT es FROM ExamSchedule es " +
            "WHERE es.status <> com.exam.model.exam.ExamStatus.CANCELLED " +
            "AND (:excludeScheduleId IS NULL OR es.scheduleId <> :excludeScheduleId) " +
            "AND es.startDateTime < :endDateTime AND :startDateTime < es.endDateTime")
    List<ExamSchedule> findOverlappingSchedules(@Param("startDateTime") LocalDateTime startDateTime,
                                                 @Param("endDateTime") LocalDateTime endDateTime,
                                                 @Param("excludeScheduleId") Long excludeScheduleId);
}
