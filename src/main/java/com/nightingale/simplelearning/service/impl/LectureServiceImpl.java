package com.nightingale.simplelearning.service.impl;

import com.nightingale.simplelearning.dao.LectureDAO;
import com.nightingale.simplelearning.model.Lecture;
import com.nightingale.simplelearning.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class LectureServiceImpl implements LectureService {

    private LectureDAO lectureDAO;

    @Autowired
    public void setLectureDAO(LectureDAO lectureDAO) {
        this.lectureDAO = lectureDAO;
    }

    @Override
    public Lecture getLectureById(BigInteger id) {
        return lectureDAO.getLectureById(id);
    }

    @Override
    public List<Lecture> getAllLecturesByCourseId(BigInteger courseId) {
        return lectureDAO.getAllLecturesByCourseId(courseId);
    }

    @Override
    public List<Lecture> getAllLectures() {
        return lectureDAO.getAllLectures();
    }

    @Override
    public boolean save(Lecture lecture, BigInteger courseId) {
        return lectureDAO.save(lecture, courseId);
    }

    @Override
    public boolean delete(BigInteger id) {
        return lectureDAO.delete(id);
    }

    @Override
    public boolean update(BigInteger id, Lecture newLecture) {
        return lectureDAO.update(id, newLecture);
    }
}
