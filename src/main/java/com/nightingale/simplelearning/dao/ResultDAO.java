package com.nightingale.simplelearning.dao;

import com.nightingale.simplelearning.model.Result;

import java.math.BigInteger;
import java.util.List;

public interface ResultDAO {

    static final String SELECT_RESULT_BY_ID = "SELECT * FROM result WHERE resultId = ?";

    static final String SELECT_RESULTS_BY_TEST_ID = "SELECT * FROM result WHERE testId = ?";

    static final String SELECT_RESULTS_BY_STUDENT_ID = "SELECT * FROM result WHERE userId = ?";

    static final String SELECT_RESULTS_BY_STUDENT_AND_TEST_ID = "SELECT * FROM result WHERE userId = ? AND testId = ?";

    static final String SELECT_FULL_RESULTS_BY_STUDENT_AND_TEST_ID = "SELECT result.resultId, test.testId, test.title, " +
            "user.userId, user.name, result.score " +
            "FROM result " +
            "JOIN USER ON user.userId = result.userId " +
            "JOIN TEST ON test.testId = result.testId " +
            "WHERE user.userId = ? AND test.testId = ?";

    static final String SELECT_ALL_RESULTS = "SELECT * FROM result";

    static final String INSERT_RESULT = "INSERT INTO result (score, testId, userId) " +
            "VALUES (?, ?, ?)";

    static final String DELETE_RESULT_BY_ID = "DELETE FROM result WHERE resultId = ?";

    static final String DELETE_RESULTS_BY_TEST_ID = "DELETE FROM test WHERE testId = ?";

    static final String DELETE_RESULTS_BY_USER_ID = "DELETE FROM test WHERE userId = ?";

    static final String UPDATE_RESULT_BY_ID = "UPDATE result " +
            "SET score = ?, testId = ?, userId = ? " +
            "WHERE resultId = ?;";


    static final String SELECT_TEST_BY_ID = "SELECT * FROM test WHERE testId = ?";

    String GET_USER_BY_ID = "SELECT user.userId, user.name, user.login, user.password, role.roleName " +
            "from user " +
            "INNER JOIN role on user.roleId = role.roleId " +
            "where userId = ?;";


    Result getResultById(BigInteger id);
    List<Result> getAllResultsByTestId(BigInteger testId);
    List<Result> getAllResultsByStudentId(BigInteger studentId);
    Result getAllResultsByStudentAndTestId(BigInteger studentId, BigInteger testId);
    List<Result> getAllResults();
    boolean deleteAllResultsByTestId(BigInteger id);
    //boolean deleteAllResultsByStudentId(BigInteger id); //TODO: Implement in case of adding student deletion
    boolean save(Result result);
    boolean delete(BigInteger id);
    boolean update(BigInteger id, Result newResult);
}