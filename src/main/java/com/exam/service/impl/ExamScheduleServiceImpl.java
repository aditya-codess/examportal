package com.exam.service.impl;

import com.exam.dto.schedule.ExamScheduleRequest;
import com.exam.dto.schedule.ExamScheduleResponse;
import com.exam.exception.InvalidRequestException;
import com.exam.exception.ResourceNotFoundException;
import com.exam.exception.SchedulingConflictException;
import com.exam.model.exam.ExamSchedule;
import com.exam.model.exam.ExamStatus;
import com.exam.model.exam.Quiz;
import com.exam.repo.ExamScheduleRepository;
import com.exam.repo.QuizRepository;
import com.exam.service.ExamScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamScheduleServiceImpl implements ExamScheduleService {

    @Autowired
    private ExamScheduleRepository examScheduleRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Override
    @Transactional
    public ExamScheduleResponse createSchedule(ExamScheduleRequest request) {
        Quiz exam = getExamOrThrow(request.getExamId());

        examScheduleRepository.findByExam(exam).ifPresent(existing -> {
            throw new InvalidRequestException(
                    "Exam with id " + request.getExamId() + " is already scheduled (scheduleId=" +
                            existing.getScheduleId() + "). Use update instead.");
        });

        validateDateRange(request.getStartDateTime(), request.getEndDateTime());
        validateNoConflicts(request.getStartDateTime(), request.getEndDateTime(), null);

        ExamSchedule schedule = new ExamSchedule();
        schedule.setExam(exam);
        schedule.setExamName(resolveExamName(request.getExamName(), exam));
        schedule.setStartDateTime(request.getStartDateTime());
        schedule.setEndDateTime(request.getEndDateTime());
        schedule.setDurationMinutes(resolveDuration(request));
        schedule.setStatus(resolveStatus(request.getStatus()));

        ExamSchedule saved = examScheduleRepository.save(schedule);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public ExamScheduleResponse updateSchedule(Long scheduleId, ExamScheduleRequest request) {
        ExamSchedule schedule = examScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam schedule not found with id: " + scheduleId));

        Quiz exam = getExamOrThrow(request.getExamId());

        // if exam is being changed, ensure the new exam isn't already scheduled elsewhere
        if (!exam.getQid().equals(schedule.getExam().getQid())) {
            examScheduleRepository.findByExam(exam).ifPresent(existing -> {
                if (!existing.getScheduleId().equals(scheduleId)) {
                    throw new InvalidRequestException(
                            "Exam with id " + request.getExamId() + " is already scheduled (scheduleId=" +
                                    existing.getScheduleId() + ")");
                }
            });
        }

        validateDateRange(request.getStartDateTime(), request.getEndDateTime());
        validateNoConflicts(request.getStartDateTime(), request.getEndDateTime(), scheduleId);

        schedule.setExam(exam);
        schedule.setExamName(resolveExamName(request.getExamName(), exam));
        schedule.setStartDateTime(request.getStartDateTime());
        schedule.setEndDateTime(request.getEndDateTime());
        schedule.setDurationMinutes(resolveDuration(request));
        schedule.setStatus(resolveStatus(request.getStatus()));

        ExamSchedule saved = examScheduleRepository.save(schedule);
        return toResponse(saved);
    }

    @Override
    public ExamScheduleResponse getScheduleById(Long scheduleId) {
        ExamSchedule schedule = examScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam schedule not found with id: " + scheduleId));
        return toResponse(schedule);
    }

    @Override
    public List<ExamScheduleResponse> getAllSchedules() {
        return examScheduleRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteSchedule(Long scheduleId) {
        if (!examScheduleRepository.existsById(scheduleId)) {
            throw new ResourceNotFoundException("Exam schedule not found with id: " + scheduleId);
        }
        examScheduleRepository.deleteById(scheduleId);
    }

    private Quiz getExamOrThrow(Long examId) {
        return quizRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam (quiz) not found with id: " + examId));
    }

    private void validateDateRange(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            throw new InvalidRequestException("startDateTime and endDateTime are required");
        }
        if (!start.isBefore(end)) {
            throw new InvalidRequestException("startDateTime must be before endDateTime");
        }
    }

    /**
     * Ensures the requested time window does not overlap any other active
     * (non-cancelled) schedule. When updating, excludeScheduleId allows the
     * schedule being updated to overlap with itself.
     */
    private void validateNoConflicts(LocalDateTime start, LocalDateTime end, Long excludeScheduleId) {
        List<ExamSchedule> overlapping = examScheduleRepository.findOverlappingSchedules(start, end, excludeScheduleId);
        if (!overlapping.isEmpty()) {
            String conflictIds = overlapping.stream()
                    .map(s -> String.valueOf(s.getScheduleId()))
                    .collect(Collectors.joining(", "));
            throw new SchedulingConflictException(
                    "Requested time window conflicts with existing schedule(s): " + conflictIds);
        }
    }

    private Long resolveDuration(ExamScheduleRequest request) {
        if (request.getDurationMinutes() != null) {
            return request.getDurationMinutes();
        }
        return Duration.between(request.getStartDateTime(), request.getEndDateTime()).toMinutes();
    }

    private String resolveExamName(String examName, Quiz exam) {
        if (examName != null && !examName.isBlank()) {
            return examName;
        }
        return exam.getTitle();
    }

    private ExamStatus resolveStatus(String status) {
        if (status == null || status.isBlank()) {
            return ExamStatus.SCHEDULED;
        }
        try {
            return ExamStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new InvalidRequestException("Invalid status: " + status +
                    ". Allowed values: SCHEDULED, ONGOING, COMPLETED, CANCELLED");
        }
    }

    private ExamScheduleResponse toResponse(ExamSchedule schedule) {
        return new ExamScheduleResponse(
                schedule.getScheduleId(),
                schedule.getExam().getQid(),
                schedule.getExamName(),
                schedule.getStartDateTime(),
                schedule.getEndDateTime(),
                schedule.getDurationMinutes(),
                schedule.getStatus().name()
        );
    }
}
