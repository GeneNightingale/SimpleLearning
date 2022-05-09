package com.nightingale.simplelearning.controller;

import com.nightingale.simplelearning.model.Result;
import com.nightingale.simplelearning.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping(value = "/api/result")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @GetMapping("/{result_id}")
    public Result getResult(@PathVariable("result_id") BigInteger id) {
        return resultService.getResultById(id);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/by_test/{test_id}")
    public List<Result> getResultsByTest(@PathVariable("test_id") BigInteger testId) {
        return resultService.getAllResultsByTestId(testId);
    }

    /*@PreAuthorize("hasRole('TEACHER')")*/
    @GetMapping("/by_course/{course_id}/{student_id}")
    public List<Result> getResultsByCourse(@PathVariable("course_id") BigInteger courseId,
                                           @PathVariable("student_id") BigInteger studentId) {
        return resultService.getAllResultsByCourseAndStudent(courseId, studentId);
    }

    //@PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/by_student/{student_id}")
    public List<Result> getResultsByStudent(@PathVariable("student_id") BigInteger studentId) {
        return resultService.getAllResultsByStudentId(studentId);
    }

    @PostMapping("/by_student_and_test")
    public Result getResultByStudentAndTest(@Valid @RequestBody Result result) {
        return resultService.getAllResultsByStudentAndTestId(
                BigInteger.valueOf(result.getStudentId()),
                BigInteger.valueOf(result.getTestId())
        );
    }

    //
    //Mostly useless stuff down below
    //
    @GetMapping
    public List<Result> getAllResults() {
        return resultService.getAllResults();
    }

    //@PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/{result_id}")
    public ResponseEntity<?> updateResult(@Valid @RequestBody Result result, @PathVariable("result_id") BigInteger id) {
        resultService.update(id, result);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/{result_id}")
    public boolean deleteResult(@PathVariable("result_id") BigInteger id) {
        return resultService.delete(id);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/by_test/{test_id}")
    public boolean deleteResultsByTestId(@PathVariable("test_id") BigInteger testId) {
        return resultService.deleteAllResultsByTestId(testId);
    }
}