package com.exam.service.impl;

import com.exam.dto.result.ResultResponse;
import com.exam.dto.result.ResultSubmissionRequest;
import com.exam.exception.InvalidRequestException;
import com.exam.exception.ResourceNotFoundException;
import com.exam.model.User;
import com.exam.model.exam.ExamResult;
import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import com.exam.model.exam.ResultStatus;
import com.exam.repo.ExamResultRepository;
import com.exam.repo.QuizRepository;
import com.exam.repo.UserRepository;
import com.exam.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ExamResultRepository examResultRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${exam.pass-percentage:40.0}")
    private double passPercentageThreshold;

    @Override
    @Transactional
    public ResultResponse submitAndCalculateResult(ResultSubmissionRequest request) {
        Quiz exam = quizRepository.findById(request.getExamId())
                .orElseThrow(() -> new ResourceNotFoundException("Exam (quiz) not found with id: " + request.getExamId()));

        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + request.getStudentId()));

        examResultRepository.findByExamAndStudent(exam, student).ifPresent(existing -> {
            throw new InvalidRequestException(
                    "A result for this student and exam already exists (resultId=" + existing.getResultId() + ")");
        });

        Set<Question> questions = exam.getQuestions();
        if (questions == null || questions.isEmpty()) {
            throw new InvalidRequestException("Exam with id " + request.getExamId() + " has no questions to score against");
        }

        double totalMarks = parseMaxMarks(exam.getMaxMarks());
        double marksPerQuestion = totalMarks / questions.size();

        Map<Long, String> answers = request.getAnswers();
        long correctCount = questions.stream()
                .filter(q -> isCorrect(q, answers))
                .count();

        double obtainedMarks = correctCount * marksPerQuestion;
        double percentage = totalMarks == 0 ? 0.0 : (obtainedMarks / totalMarks) * 100.0;
        ResultStatus status = percentage >= passPercentageThreshold ? ResultStatus.PASS : ResultStatus.FAIL;

        ExamResult result = new ExamResult();
        result.setExam(exam);
        result.setStudent(student);
        result.setTotalMarks(round(totalMarks));
        result.setObtainedMarks(round(obtainedMarks));
        result.setPercentage(round(percentage));
        result.setStatus(status);
        result.setSubmittedAt(request.getSubmittedAt() != null ? request.getSubmittedAt() : LocalDateTime.now());

        ExamResult saved = examResultRepository.save(result);
        return toResponse(saved);
    }

    @Override
    public ResultResponse getResultById(Long resultId) {
        ExamResult result = examResultRepository.findById(resultId)
                .orElseThrow(() -> new ResourceNotFoundException("Result not found with id: " + resultId));
        return toResponse(result);
    }

    @Override
    public List<ResultResponse> getResultsByStudent(Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
        return examResultRepository.findByStudent(student).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResultResponse> getResultsByExam(Long examId) {
        Quiz exam = quizRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam (quiz) not found with id: " + examId));
        return examResultRepository.findByExam(exam).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private boolean isCorrect(Question question, Map<Long, String> answers) {
        if (answers == null) {
            return false;
        }
        String submitted = answers.get(question.getQuesId());
        String correct = question.getAnswer();
        if (submitted == null || correct == null) {
            return false;
        }
        return submitted.trim().equalsIgnoreCase(correct.trim());
    }

    private double parseMaxMarks(String maxMarks) {
        if (maxMarks == null || maxMarks.isBlank()) {
            throw new InvalidRequestException("Exam's maxMarks is not configured");
        }
        try {
            return Double.parseDouble(maxMarks.trim());
        } catch (NumberFormatException ex) {
            throw new InvalidRequestException("Exam's maxMarks is not a valid number: " + maxMarks);
        }
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private ResultResponse toResponse(ExamResult result) {
        return new ResultResponse(
                result.getResultId(),
                result.getExam().getQid(),
                result.getExam().getTitle(),
                result.getStudent().getId(),
                result.getStudent().getUsername(),
                result.getTotalMarks(),
                result.getObtainedMarks(),
                result.getPercentage(),
                result.getStatus().name(),
                result.getSubmittedAt(),
                result.getRank()
        );
    }
}
