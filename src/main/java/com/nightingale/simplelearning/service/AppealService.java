package com.nightingale.simplelearning.service;

import com.nightingale.simplelearning.model.Appeal;

import java.math.BigInteger;
import java.util.List;

@Deprecated
public interface AppealService {
    public Appeal getAppealById(BigInteger id);
    public List<Appeal> getAllAppeals();
    public List<Appeal> getAllAppealsByTeacher(BigInteger teacherId);
    public List<Appeal> getAllAppealsByStudent(BigInteger studentId);
    public List<Appeal> getAllAppealsByCourseId(BigInteger courseId);
    public boolean approveAppealById(BigInteger id);
    public boolean denyAppealById(BigInteger id);
    //public boolean deleteClosedAppealsByTeacher(BigInteger id);
    public boolean cleanUpByTeacher();
    public boolean deleteAllAppealsByCourseId(BigInteger courseId);
    public boolean save(Appeal appeal, BigInteger courseId);
    public boolean delete(BigInteger id);
    public boolean update(BigInteger id, Appeal newAppeal);

    public List<Appeal> getAllAppealsByCurrentUser();
}
