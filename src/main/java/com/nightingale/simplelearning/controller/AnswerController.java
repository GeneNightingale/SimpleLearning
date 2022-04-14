package com.nightingale.simplelearning.controller;

import com.nightingale.simplelearning.model.Answer;
import com.nightingale.simplelearning.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping(value = "/api/answer")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/by_question/{question_id}")
    public ResponseEntity<?> addAnswer(@Valid @RequestBody Answer answer, @PathVariable("question_id") BigInteger id) {
        answerService.save(answer, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public List<Answer> getAllAnswers() {
        return answerService.getAllAnswers();
    }

    @GetMapping("/{answer_id}")
    public Answer getAnswer(@PathVariable("answer_id") BigInteger id) {
        return answerService.getAnswerById(id);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/{answer_id}")
    public ResponseEntity<?> updateAnswer(@Valid @RequestBody Answer answer, @PathVariable("answer_id") BigInteger id) {
        answerService.update(id, answer);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/{answer_id}")
    public boolean deleteAnswer(@PathVariable("answer_id") BigInteger id) {
        return answerService.delete(id);
    }

    @GetMapping("/by_question/{question_id}")
    public List<Answer> getAnswersByQuestionId(@PathVariable("question_id") BigInteger id) {
        return answerService.getAllAnswersByQuestionId(id);
    }
}