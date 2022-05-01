package com.nightingale.simplelearning.service;

import com.nightingale.simplelearning.controller.request.RequestTest;
import com.nightingale.simplelearning.model.Test;

import java.math.BigInteger;
import java.util.List;

public interface TestService {
    Test getTestById(BigInteger id);
    Test getTestByIdNoAnswer(BigInteger id);
    List<Test> getAllTestsByCourseId(BigInteger testId);
    List<Test> getAllTests();
    //boolean deleteAllTestsByCourseId(BigInteger id);
    boolean completeTest(RequestTest requestTest, BigInteger testId);
    boolean makePublic(BigInteger testId);
    boolean makePrivate(BigInteger testId);
    boolean save(Test test, BigInteger courseId);
    boolean delete(BigInteger id);
    boolean update(BigInteger id, Test newTest);
}
