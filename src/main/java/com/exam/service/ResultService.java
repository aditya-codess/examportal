package com.exam.service;

import com.exam.dto.result.ResultResponse;
import com.exam.dto.result.ResultSubmissionRequest;

import java.util.List;

public interface ResultService {


    ResultResponse submitAndCalculateResult(ResultSubmissionRequest request);

    ResultResponse getResultById(Long resultId);

    List<ResultResponse> getResultsByStudent(Long studentId);

    List<ResultResponse> getResultsByExam(Long examId);
}
