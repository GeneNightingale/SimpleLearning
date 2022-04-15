package com.nightingale.simplelearning.dao.impl;

import com.nightingale.simplelearning.dao.LectureDAO;
import com.nightingale.simplelearning.dao.PageDAO;
import com.nightingale.simplelearning.dao.mapper.LectureRowMapper;
import com.nightingale.simplelearning.model.Lecture;
import com.nightingale.simplelearning.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class LectureDAOImpl implements LectureDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PageDAO pageDAO;

    @Autowired
    private LectureRowMapper lectureRowMapper;

    private static final Logger LOGGER = Logger.getLogger(LectureDAOImpl.class.getName());

    @Override
    public Lecture getLectureById(BigInteger id) {
        try {
            Lecture lecture = jdbcTemplate.queryForObject(SELECT_LECTURE_BY_ID, lectureRowMapper, id);
            if (lecture != null) {
                List<Page> pages = pageDAO.getAllPagesByLectureId(id);
                lecture.setPages(pages);
            }
            return lecture;
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    public List<Lecture> getAllLecturesByCourseId(BigInteger courseId) {
        try {
            return jdbcTemplate.query(SELECT_LECTURES_BY_COURSE_ID, lectureRowMapper, courseId);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    public List<Lecture> getAllLectures() {
        try {
            return jdbcTemplate.query(SELECT_ALL_LECTURES, lectureRowMapper);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = {DataAccessException.class})
    public boolean deleteAllLecturesByCourseId(BigInteger id) {
        try {
            jdbcTemplate.update(DELETE_ALL_LECTURES_BY_COURSE_ID, id);
            return true;
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = {DataAccessException.class})
    public boolean save(Lecture lecture, BigInteger courseId) {
        if(isInvalidLecture(lecture)){
            LOGGER.log(Level.WARNING, "Invalid Lecture");
            return false;
        }
        try {
            jdbcTemplate.update(INSERT_LECTURE_TO_COURSE, lecture.getTitle(), courseId);
            return true;
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class)
    public boolean delete(BigInteger id) {
        try {
            Lecture lecture = getLectureById(id);
            if (lecture != null) {
                //TRY TO DELETE LECTURE'S PAGES FIRST
                boolean pageDeletionSuccessfulResult = pageDAO.deleteAllPagesByLectureId(id);
                //IF THAT'S DONE, TRY TO DELETE LECTURE ITSELF
                if (pageDeletionSuccessfulResult) {
                    jdbcTemplate.update(DELETE_LECTURE_BY_ID, id);
                    return true;
                } else
                    return false;
            } else
                return false;
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class)
    public boolean update(BigInteger id, Lecture newLecture) {
        if(isInvalidLecture(newLecture)){
            LOGGER.log(Level.WARNING, "Invalid Lecture");
            return false;
        }
        try {
            Lecture lecture = getLectureById(id);
            if (lecture!=null) {
                jdbcTemplate.update(UPDATE_LECTURE_BY_ID, newLecture.getTitle(), id);
                return true;
            } else {
                LOGGER.log(Level.WARNING, "No Lecture with given ID");
                return false;
            }
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    private boolean isInvalidLecture(Lecture lecture) {
        if (lecture == null)
            return true;
        else
            return lecture.getTitle() == null;
    }

}

