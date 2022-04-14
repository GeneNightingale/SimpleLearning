package com.nightingale.simplelearning.dao;

import com.nightingale.simplelearning.model.Appeal;

import java.math.BigInteger;
import java.util.List;

public interface AppealDAO {

    String SELECT_APPEAL_BY_ID = "SELECT * FROM appeal WHERE appealId = ?;";

    String SELECT_APPEALS_BY_COURSE_ID = "SELECT * FROM appeal WHERE courseId = ?;";

    String SELECT_ALL_APPEALS = "SELECT * FROM appeal;";

    String SELECT_ALL_APPEALS_BY_TEACHER = "SELECT * FROM appeal join course on course.courseId = appeal.courseId " +
                                            "WHERE course.teacherId = ?;";

    String SELECT_ALL_APPEALS_BY_STUDENT = "SELECT * FROM appeal WHERE appeal.userId = ?;";

    String APPROVE_APPEAL_BY_ID = "UPDATE appeal " +
            "SET status = 'APPROVED' " +
            "WHERE appealId = ? ";

    String DENY_APPEAL_BY_ID = "UPDATE appeal " +
            "SET status = 'DENIED', reasonForDenial = ? " +
            "WHERE appealId = ? ";

    String INSERT_APPEAL = "INSERT INTO appeal (courseId, userId, text) " +
            "VALUES (?, ?, ?)";

    String DELETE_CLOSED_APPEALS_BY_TEACHER = "DELETE FROM appeal WHERE id IN ( " +
            "              select * from " +
            "              (" +
            "                   SELECT * FROM appeal join course on course.courseId = appeal.courseId " +
            "                   WHERE course.teacherId = ?" +
            "              ) " +
            ");";

    String DELETE_APPEAL_BY_ID = "DELETE FROM appeal WHERE appealId = ?;";

    String DELETE_All_APPEALS_BY_COURSE_ID = "DELETE FROM appeal WHERE courselId = ?;";

    String UPDATE_APPEAL_BY_ID = "UPDATE appeal " +
            "SET text = ?, status = ?, reasonForDenial = ? " +
            "WHERE courseId = ?;";

    public Appeal getAppealById(BigInteger id);
    public List<Appeal> getAllAppeals();
    public List<Appeal> getAllAppealsByTeacher(BigInteger teacherId);
    public List<Appeal> getAllAppealsByStudent(BigInteger studentId);
    public List<Appeal> getAllAppealsByCourseId(BigInteger courseId);
    public boolean approveAppealById(BigInteger id);
    public boolean denyAppealById(BigInteger id);
    public boolean deleteClosedAppealsByTeacher(BigInteger id);
    public boolean deleteAllAppealsByCourseId(BigInteger courseId);
    public boolean save(Appeal appeal, BigInteger courseId);
    public boolean delete(BigInteger id);
    public boolean update(BigInteger id, Appeal newAppeal);

}
