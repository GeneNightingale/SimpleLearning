package com.nightingale.simplelearning.service;

import com.nightingale.simplelearning.model.Result;

import java.math.BigInteger;
import java.util.List;

public interface ResultService {
    Result getResultById(BigInteger id);
    List<Result> getAllResultsByTestId(BigInteger testId);
    List<Result> getAllResultsByCourseAndStudent(BigInteger courseId, BigInteger studentId);
    List<Result> getAllResultsByStudentId(BigInteger studentId);
    Result getAllResultsByStudentAndTestId(BigInteger studentId, BigInteger testId);
    List<Result> getAllResults();
    boolean deleteAllResultsByTestId(BigInteger id);
    //boolean deleteAllResultsByStudentId(BigInteger id);
    boolean saveResultByCurrentUser(Result result);
    boolean save(Result result);
    boolean delete(BigInteger id);
    boolean update(BigInteger id, Result newResult);
}
