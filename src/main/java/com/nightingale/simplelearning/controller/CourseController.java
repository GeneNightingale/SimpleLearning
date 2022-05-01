package com.nightingale.simplelearning.controller;

import com.nightingale.simplelearning.model.Course;
import com.nightingale.simplelearning.model.User;
import com.nightingale.simplelearning.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/api/course")
public class CourseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping
    public ResponseEntity<?> saveCourse(@Valid @RequestBody Course course) {
        courseService.saveCourseAsCurrentTeacher(course);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{course_id}")
    public Course getCourse(@PathVariable("course_id") BigInteger course_id) {
        return courseService.getCourseById(course_id);
    }

    //@PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/my_courses")
    public List<Course> getMyCourses() {
        return courseService.getCoursesByCurrentUser();
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/by_teacher/{teacher_id}")
    public List<Course> getCoursesByTeacher(@PathVariable("teacher_id") BigInteger teacherId) {
        return courseService.getAllCoursesByTeacher(teacherId);
    }

    //@PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/by_student/{student_id}")
    public List<Course> getCoursesByStudent(@PathVariable("student_id") BigInteger studentId) {
        return courseService.getAllCoursesByStudent(studentId);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/students_by_course_id/{course_id}")
    public List<User> getStudentsByCourseId(@PathVariable("course_id") BigInteger courseId) {
        return courseService.getAllStudentsByCourseId(courseId);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/not_students_by_course_id/{course_id}")
    public List<User> getNotStudentsByCourseId(@PathVariable("course_id") BigInteger courseId) {
        return courseService.getAllNotStudentsByCourseId(courseId);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/add_member_to_course/{course_id}")
    public ResponseEntity<?> addMemberToCourse(@PathVariable("course_id") BigInteger courseId, @RequestBody BigInteger studentId) {
        courseService.addCourseMember(courseId, studentId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/remove_member_from_course/{course_id}")
    public ResponseEntity<?> removeMemberFromCourse(@PathVariable("course_id") BigInteger courseId, @RequestBody BigInteger studentId) {
        courseService.removeCourseMember(courseId, studentId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/make_public/{course_id}")
    public ResponseEntity<?> makePublic(@PathVariable("course_id") BigInteger courseId) {
        courseService.makePublic(courseId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/make_private/{course_id}")
    public ResponseEntity<?> makePrivate(@PathVariable("course_id") BigInteger courseId) {
        courseService.makePrivate(courseId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/{course_id}")
    public ResponseEntity<?> updateCourse(@Valid @RequestBody Course course, @PathVariable("course_id") BigInteger id) {
        courseService.update(id, course);
        System.out.println(course.getTitle() + course.getDescription());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/{course_id}")
    public boolean deleteCourse(@PathVariable("course_id") BigInteger id) {
        return courseService.delete(id);
    }

    //
    //Mostly useless stuff down below
    //

    //TODO: Change perms
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

}
