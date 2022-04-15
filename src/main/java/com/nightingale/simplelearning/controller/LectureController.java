package com.nightingale.simplelearning.controller;

import com.nightingale.simplelearning.model.Lecture;
import com.nightingale.simplelearning.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping(value = "/api/lecture")
public class LectureController {

    @Autowired
    private LectureService lectureService;

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/by_course/{course_id}")
    public ResponseEntity<?> addLecture(@Valid @RequestBody Lecture lecture, @PathVariable("course_id") BigInteger id) {
        lectureService.save(lecture, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public List<Lecture> getAllLectures() {
        return lectureService.getAllLectures();
    }

    @GetMapping("/{lecture_id}")
    public Lecture getLecture(@PathVariable("lecture_id") BigInteger id) {
        return lectureService.getLectureById(id);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/{lecture_id}")
    public ResponseEntity<?> updateLecture(@Valid @RequestBody Lecture lecture, @PathVariable("lecture_id") BigInteger id) {
        lectureService.update(id, lecture);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/{lecture_id}")
    public boolean deleteLecture(@PathVariable("lecture_id") BigInteger id) {
        return lectureService.delete(id);
    }

    @GetMapping("/by_course/{course_id}")
    public List<Lecture> getLecturesByCourseId(@PathVariable("course_id") BigInteger id) {
        return lectureService.getAllLecturesByCourseId(id);
    }
}
