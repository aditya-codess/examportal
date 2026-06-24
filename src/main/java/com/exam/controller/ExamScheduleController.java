package com.exam.controller;

import com.exam.dto.schedule.ExamScheduleRequest;
import com.exam.dto.schedule.ExamScheduleResponse;
import com.exam.service.ExamScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exam-schedule")
@CrossOrigin("*")
public class ExamScheduleController {

    @Autowired
    private ExamScheduleService examScheduleService;

    @PostMapping("/")
    public ResponseEntity<ExamScheduleResponse> create(@Valid @RequestBody ExamScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(examScheduleService.createSchedule(request));
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<ExamScheduleResponse> update(@PathVariable("scheduleId") Long scheduleId,
                                                        @Valid @RequestBody ExamScheduleRequest request) {
        return ResponseEntity.ok(examScheduleService.updateSchedule(scheduleId, request));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ExamScheduleResponse> getById(@PathVariable("scheduleId") Long scheduleId) {
        return ResponseEntity.ok(examScheduleService.getScheduleById(scheduleId));
    }

    @GetMapping("/")
    public ResponseEntity<List<ExamScheduleResponse>> getAll() {
        return ResponseEntity.ok(examScheduleService.getAllSchedules());
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> delete(@PathVariable("scheduleId") Long scheduleId) {
        examScheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.noContent().build();
    }
}
