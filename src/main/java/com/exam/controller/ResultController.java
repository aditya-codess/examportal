package com.exam.controller;

import com.exam.dto.result.ResultResponse;
import com.exam.dto.result.ResultSubmissionRequest;
import com.exam.service.ResultService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/result")
@CrossOrigin("*")
public class ResultController {

    @Autowired
    private ResultService resultService;

    /**
     * Submits a student's exam answers and automatically calculates + stores the result.
     */
    @PostMapping("/submit")
    public ResponseEntity<ResultResponse> submit(@Valid @RequestBody ResultSubmissionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(resultService.submitAndCalculateResult(request));
    }

    @GetMapping("/{resultId}")
    public ResponseEntity<ResultResponse> getById(@PathVariable("resultId") Long resultId) {
        return ResponseEntity.ok(resultService.getResultById(resultId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ResultResponse>> getByStudent(@PathVariable("studentId") Long studentId) {
        return ResponseEntity.ok(resultService.getResultsByStudent(studentId));
    }

    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<ResultResponse>> getByExam(@PathVariable("examId") Long examId) {
        return ResponseEntity.ok(resultService.getResultsByExam(examId));
    }
}
