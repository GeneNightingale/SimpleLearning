package com.nightingale.simplelearning.dao.mapper;

import com.nightingale.simplelearning.model.Test;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TestRowMapper implements RowMapper<Test> {
    @Override
    public Test mapRow(ResultSet rs, int rowNum) throws SQLException {

        Test test = new Test();

        test.setTestId(rs.getInt("testId"));
        test.setTitle(rs.getString("title"));
        test.setTime(rs.getInt("time"));
        //TODO: DO SOMETHING ABOUT THE QUESTIONS

        return test;

    }
}