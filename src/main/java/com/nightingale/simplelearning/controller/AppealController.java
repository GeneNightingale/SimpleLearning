package com.nightingale.simplelearning.controller;

import com.nightingale.simplelearning.model.Appeal;
import com.nightingale.simplelearning.service.AppealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;

@Deprecated
@RestController
@RequestMapping(value = "/api/appeal")
public class AppealController {

    @Autowired
    private AppealService appealService;

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/by_course/{course_id}")
    public ResponseEntity<?> makeAppeal(@Valid @RequestBody Appeal appeal, @PathVariable("course_id") BigInteger id) {
        appealService.save(appeal, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/approve/{appeal_id}")
    public ResponseEntity<?> approveAppeal(@PathVariable("appeal_id") BigInteger id) {
        appealService.approveAppealById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/deny/{appeal_id}")
    public ResponseEntity<?> denyAppeal(@PathVariable("appeal_id") BigInteger id) {
        appealService.denyAppealById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{appeal_id}")
    public Appeal getAppeal(@PathVariable("appeal_id") BigInteger id) {
        return appealService.getAppealById(id);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/teacher_appeals")
    public List<Appeal> getTeacherAppeals() {
        return appealService.getAllAppealsByCurrentUser();
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/student_appeals")
    public List<Appeal> getStudentAppeals() {
        return appealService.getAllAppealsByCurrentUser();
    }

    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/clean_up")
    public boolean cleanUpByTeacher() {
        return appealService.cleanUpByTeacher();
    }

    //
    //Mostly useless stuff down below
    //
    @GetMapping
    public List<Appeal> getAllAppeals() {
        return appealService.getAllAppeals();
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/{appeal_id}")
    public ResponseEntity<?> updateAppeal(@Valid @RequestBody Appeal appeal, @PathVariable("appeal_id") BigInteger id) {
        appealService.update(id, appeal);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/{appeal_id}")
    public boolean deleteAppeal(@PathVariable("appeal_id") BigInteger id) {
        return appealService.delete(id);
    }

    @GetMapping("/by_course/{course_id}")
    public List<Appeal> getAppealsByCourseId(@PathVariable("course_id") BigInteger id) {
        return appealService.getAllAppealsByCourseId(id);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/by_course/{course_id}")
    public boolean deleteAppealsByCourseId(@PathVariable("course_id") BigInteger id) {
        return appealService.deleteAllAppealsByCourseId(id);
    }
}