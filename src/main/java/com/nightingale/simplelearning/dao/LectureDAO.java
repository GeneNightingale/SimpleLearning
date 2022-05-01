package com.nightingale.simplelearning.dao;

import com.nightingale.simplelearning.model.Lecture;

import java.math.BigInteger;
import java.util.List;

public interface LectureDAO {

    static final String SELECT_LECTURE_BY_ID = "SELECT * FROM lecture where lectureId = ?";

    static final String SELECT_LECTURES_BY_COURSE_ID = "SELECT * FROM lecture WHERE courseId = ?";

    static final String SELECT_LECTURES_BY_COURSE_ID_STUDENT = "SELECT * FROM lecture WHERE isPublic = 'true' AND courseId = ?";

    static final String SELECT_ALL_LECTURES = "SELECT * FROM lecture";

    static final String INSERT_LECTURE_TO_COURSE = "INSERT INTO lecture (title, courseId) VALUES (?, ?)";

    static final String DELETE_LECTURE_BY_ID = "DELETE FROM lecture WHERE lectureId = ?";

    static final String DELETE_ALL_LECTURES_BY_COURSE_ID = "DELETE FROM lecture WHERE courseId = ?";

    static final String UPDATE_LECTURE_BY_ID = "UPDATE lecture " +
            "SET title = ? " +
            "WHERE lectureId = ?;";

    String MAKE_PUBLIC = "UPDATE lecture SET isPublic = 'true' WHERE lectureId = ?;";

    String MAKE_PRIVATE = "UPDATE lecture SET isPublic = 'false' WHERE lectureId = ?;";

    Lecture getLectureById(BigInteger id);
    List<Lecture> getAllLecturesByCourseId(BigInteger courseId);
    List<Lecture> getAllLecturesByCourseIdStudent(BigInteger courseId);
    List<Lecture> getAllLectures();
    boolean deleteAllLecturesByCourseId(BigInteger id);
    boolean makePublic(BigInteger lectureId);
    boolean makePrivate(BigInteger lectureId);
    boolean save(Lecture lecture, BigInteger courseId);
    boolean delete(BigInteger id);
    boolean update(BigInteger id, Lecture newLecture);
}
