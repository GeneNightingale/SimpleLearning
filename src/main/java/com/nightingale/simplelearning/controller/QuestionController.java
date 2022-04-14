package com.nightingale.simplelearning.controller;

import com.nightingale.simplelearning.model.Lecture;
import com.nightingale.simplelearning.model.Question;
import com.nightingale.simplelearning.service.LectureService;
import com.nightingale.simplelearning.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping(value = "/api/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/by_test/{test_id}")
    public ResponseEntity<?> addQuestion(@Valid @RequestBody Question question, @PathVariable("test_id") BigInteger id) {
        questionService.save(question, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/{question_id}")
    public Question getQuestion(@PathVariable("question_id") BigInteger id) {
        return questionService.getQuestionById(id);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/{question_id}")
    public ResponseEntity<?> updateQuestion(@Valid @RequestBody Question question, @PathVariable("question_id") BigInteger id) {
        questionService.update(id, question);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/{question_id}")
    public boolean deleteQuestion(@PathVariable("question_id") BigInteger id) {
        return questionService.delete(id);
    }

    @GetMapping("/by_test/{test_id}")
    public List<Question> getQuestionsByTestId(@PathVariable("test_id") BigInteger id) {
        return questionService.getAllQuestionsByTestId(id);
    }
}