package com.nightingale.simplelearning.dao.mapper;

import com.nightingale.simplelearning.model.Result;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ResultRowMapper implements RowMapper<Result> {
    @Override
    public Result mapRow(ResultSet rs, int rowNum) throws SQLException {

        Result result = new Result();

        result.setResultId(rs.getInt("resultId"));
        result.setScore(rs.getBigDecimal("score"));
        result.setStudentId(rs.getInt("userId"));
        result.setTestId(rs.getInt("testId"));
        
        return result;

    }
}