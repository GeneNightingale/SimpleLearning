package com.nightingale.simplelearning.service;

import com.nightingale.simplelearning.model.Lecture;

import java.math.BigInteger;
import java.util.List;

public interface LectureService {
    Lecture getLectureById(BigInteger id);
    List<Lecture> getAllLecturesByCourseId(BigInteger courseId);
    List<Lecture> getAllLectures();
    boolean save(Lecture lecture, BigInteger courseId);
    boolean delete(BigInteger id);
    boolean update(BigInteger id, Lecture newLecture);
}
