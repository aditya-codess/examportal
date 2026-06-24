package com.exam.controller;

import com.exam.dto.merit.MeritListEntryResponse;
import com.exam.service.MeritListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/merit-list")
@CrossOrigin("*")
public class MeritListController {

    @Autowired
    private MeritListService meritListService;

    /**
     * Generates (and persists ranks for) the full merit list for an exam.
     */
    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<MeritListEntryResponse>> getMeritList(@PathVariable("examId") Long examId) {
        return ResponseEntity.ok(meritListService.generateMeritList(examId));
    }

    @GetMapping("/exam/{examId}/top/{n}")
    public ResponseEntity<List<MeritListEntryResponse>> getTopN(@PathVariable("examId") Long examId,
                                                                 @PathVariable("n") int n) {
        return ResponseEntity.ok(meritListService.getTopN(examId, n));
    }

    @GetMapping("/exam/{examId}/student/{studentId}")
    public ResponseEntity<MeritListEntryResponse> getStudentRank(@PathVariable("examId") Long examId,
                                                                  @PathVariable("studentId") Long studentId) {
        return ResponseEntity.ok(meritListService.getStudentRank(examId, studentId));
    }
}
