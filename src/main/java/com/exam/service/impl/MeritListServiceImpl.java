package com.exam.service.impl;

import com.exam.dto.merit.MeritListEntryResponse;
import com.exam.exception.ResourceNotFoundException;
import com.exam.model.exam.ExamResult;
import com.exam.model.exam.Quiz;
import com.exam.repo.ExamResultRepository;
import com.exam.repo.QuizRepository;
import com.exam.service.MeritListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeritListServiceImpl implements MeritListService {

    @Autowired
    private ExamResultRepository examResultRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Override
    @Transactional
    public List<MeritListEntryResponse> generateMeritList(Long examId) {
        Quiz exam = quizRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam (quiz) not found with id: " + examId));

        // Ordered by obtainedMarks desc, then percentage desc, then submittedAt asc (earlier submission wins ties)
        List<ExamResult> ordered = examResultRepository
                .findByExamOrderByObtainedMarksDescPercentageDescSubmittedAtAsc(exam);

        List<MeritListEntryResponse> entries = new ArrayList<>();
        int rank = 1;
        for (ExamResult result : ordered) {
            result.setRank(rank);
            examResultRepository.save(result);
            entries.add(toEntry(result, rank));
            rank++;
        }
        return entries;
    }

    @Override
    public List<MeritListEntryResponse> getTopN(Long examId, int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be a positive integer");
        }
        List<MeritListEntryResponse> meritList = generateMeritList(examId);
        return meritList.stream().limit(n).collect(Collectors.toList());
    }

    @Override
    public MeritListEntryResponse getStudentRank(Long examId, Long studentId) {
        List<MeritListEntryResponse> meritList = generateMeritList(examId);
        return meritList.stream()
                .filter(entry -> entry.getStudentId().equals(studentId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No result found for student id " + studentId + " in exam id " + examId));
    }

    private MeritListEntryResponse toEntry(ExamResult result, int rank) {
        return new MeritListEntryResponse(
                rank,
                result.getStudent().getId(),
                result.getStudent().getUsername(),
                result.getObtainedMarks(),
                result.getPercentage(),
                result.getSubmittedAt()
        );
    }
}
