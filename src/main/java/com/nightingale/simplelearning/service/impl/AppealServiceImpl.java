package com.nightingale.simplelearning.service.impl;

import com.nightingale.simplelearning.dao.AppealDAO;
import com.nightingale.simplelearning.model.Appeal;
import com.nightingale.simplelearning.model.User;
import com.nightingale.simplelearning.model.enums.Status;
import com.nightingale.simplelearning.service.AppealService;
import com.nightingale.simplelearning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class AppealServiceImpl implements AppealService {

    private AppealDAO appealDAO;

    @Autowired
    private UserService userService;

    @Autowired
    public void setAppealDAO(AppealDAO appealDAO) {
        this.appealDAO = appealDAO;
    }

    @Override
    public Appeal getAppealById(BigInteger id) {
        return appealDAO.getAppealById(id);
    }

    @Override
    public List<Appeal> getAllAppeals() {
        return appealDAO.getAllAppeals();
    }

    @Override
    public List<Appeal> getAllAppealsByTeacher(BigInteger teacherId) {
        return appealDAO.getAllAppealsByTeacher(teacherId);
    }

    @Override
    public List<Appeal> getAllAppealsByStudent(BigInteger studentId) {
        return appealDAO.getAllAppealsByStudent(studentId);
    }

    @Override
    public List<Appeal> getAllAppealsByCourseId(BigInteger courseId) {
        return appealDAO.getAllAppealsByCourseId(courseId);
    }

    @Override
    public boolean approveAppealById(BigInteger id) {
        return appealDAO.approveAppealById(id);
    }

    @Override
    public boolean denyAppealById(BigInteger id) {
        return appealDAO.denyAppealById(id);
    }

    /*@Override
    public boolean deleteClosedAppealsByTeacher(BigInteger id) {
        return appealDAO.deleteClosedAppealsByTeacher(id);
    }*/

    @Override
    public boolean cleanUpByTeacher() {
        User currentUser = userService.getCurrentUser();
        if ("TEACHER".equals(currentUser.getRole().toString())) {
            return appealDAO.deleteClosedAppealsByTeacher(BigInteger.valueOf(currentUser.getUserId()));
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteAllAppealsByCourseId(BigInteger courseId) {
        return appealDAO.deleteAllAppealsByCourseId(courseId);
    }

    @Override
    public boolean save(Appeal appeal, BigInteger courseId) {
        return appealDAO.save(appeal, courseId);
    }

    @Override
    public boolean delete(BigInteger id) {
        return appealDAO.delete(id);
    }

    @Override
    public boolean update(BigInteger id, Appeal newAppeal) {
        return appealDAO.update(id, newAppeal);
    }

    @Override
    public List<Appeal> getAllAppealsByCurrentUser() {
        User currentUser = userService.getCurrentUser();
        List<Appeal> appeals;
        switch (currentUser.getRole().toString()) {
            case "STUDENT":
                appeals = getAllAppealsByStudent(BigInteger.valueOf(currentUser.getUserId()));
                break;
            case "TEACHER":
                appeals = getAllAppealsByTeacher(BigInteger.valueOf(currentUser.getUserId()));
                break;
            default:
                return null;
        }
        return appeals;
    }
}
