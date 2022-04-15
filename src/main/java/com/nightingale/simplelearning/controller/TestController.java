package com.nightingale.simplelearning.controller;

import com.nightingale.simplelearning.controller.request.RequestTest;
import com.nightingale.simplelearning.model.Test;
import com.nightingale.simplelearning.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;

//TODO: THIS WILL NEED A REQUEST TEST WITH STUDENT'S ANSWERS
//      AS WELL AS A CHECK TO SEE IF THEY ARE RIGHT AND
//      GENERATE A RESULT
//NOTE: Accomplished, will need further testing
@RestController
@RequestMapping(value = "/api/test")
public class TestController {

    @Autowired
    private TestService testService;

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/by_course/{course_id}")
    public ResponseEntity<?> addTest(@Valid @RequestBody Test test, @PathVariable("course_id") BigInteger id) {
        testService.save(test, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //TODO: Actually check if this works
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/complete_test/{test_id}")
    public ResponseEntity<?> completeTest(@Valid @RequestBody RequestTest requestTest, @PathVariable("test_id") BigInteger testId) {
        testService.completeTest(requestTest, testId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public List<Test> getAllTests() {
        return testService.getAllTests();
    }

    @GetMapping("/{test_id}")
    public Test getTest(@PathVariable("test_id") BigInteger id) {
        return testService.getTestByIdNoAnswer(id);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/{test_id}")
    public ResponseEntity<?> updateTest(@Valid @RequestBody Test test, @PathVariable("test_id") BigInteger id) {
        testService.update(id, test);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/{test_id}")
    public boolean deleteTest(@PathVariable("test_id") BigInteger id) {
        return testService.delete(id);
    }

    @GetMapping("/by_course/{course_id}")
    public List<Test> getTestsByCourseId(@PathVariable("course_id") BigInteger id) {
        return testService.getAllTestsByCourseId(id);
    }
}