package com.exam.service;

import com.exam.dto.schedule.ExamScheduleRequest;
import com.exam.dto.schedule.ExamScheduleResponse;

import java.util.List;

public interface ExamScheduleService {

    ExamScheduleResponse createSchedule(ExamScheduleRequest request);

    ExamScheduleResponse updateSchedule(Long scheduleId, ExamScheduleRequest request);

    ExamScheduleResponse getScheduleById(Long scheduleId);

    List<ExamScheduleResponse> getAllSchedules();

    void deleteSchedule(Long scheduleId);
}
