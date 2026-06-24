package com.exam.service;

import com.exam.dto.merit.MeritListEntryResponse;

import java.util.List;

public interface MeritListService {


    List<MeritListEntryResponse> generateMeritList(Long examId);

    List<MeritListEntryResponse> getTopN(Long examId, int n);

    MeritListEntryResponse getStudentRank(Long examId, Long studentId);
}
