package com.nightingale.simplelearning.dao;

import com.nightingale.simplelearning.model.Test;

import java.math.BigInteger;
import java.util.List;

public interface TestDAO {

    static final String SELECT_TEST_BY_ID = "SELECT * FROM test WHERE testId = ?";

    static final String SELECT_TESTS_BY_COURSE_ID = "SELECT * FROM test WHERE courseId = ?";

    static final String SELECT_TEST_BY_RESULT_ID = "SELECT * FROM test join result ON " +
            "test.testId = result.testId WHERE result.resultId = ?";

    static final String SELECT_ALL_TESTS = "SELECT * FROM test";

    static final String INSERT_TEST_TO_COURSE = "INSERT INTO test (title, time, courseId) " +
            "VALUES (?, ?, ?)";

    static final String DELETE_TEST_BY_ID = "DELETE FROM test WHERE testId = ?";

    static final String DELETE_TESTS_BY_COURSE_ID = "DELETE FROM test WHERE courseId = ?";

    static final String UPDATE_TEST_BY_ID = "UPDATE test " +
            "SET title = ?, time = ? " +
            "WHERE testId = ?;";

    Test getTestById(BigInteger id);
    List<Test> getAllTestsByCourseId(BigInteger testId);
    Test getTestByResultId(BigInteger id);
    List<Test> getAllTests();
    boolean deleteAllTestsByCourseId(BigInteger id);
    boolean save(Test test, BigInteger courseId);
    boolean delete(BigInteger id);
    boolean update(BigInteger id, Test newTest);
}
