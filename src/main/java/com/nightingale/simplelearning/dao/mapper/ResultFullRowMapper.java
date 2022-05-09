package com.nightingale.simplelearning.dao.mapper;

import com.nightingale.simplelearning.model.Result;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ResultFullRowMapper implements RowMapper<Result> {
    @Override
    public Result mapRow(ResultSet rs, int rowNum) throws SQLException {

        Result result = new Result();

        result.setResultId(rs.getInt("resultId"));
        result.setScore(rs.getBigDecimal("score"));
        result.setStudentId(rs.getInt("userId"));
        result.setName(rs.getString("name"));
        result.setTestId(rs.getInt("testId"));
        result.setTitle(rs.getString("title"));

        return result;

    }
}