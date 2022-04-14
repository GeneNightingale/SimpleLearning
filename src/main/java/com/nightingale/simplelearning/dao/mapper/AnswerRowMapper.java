package com.nightingale.simplelearning.dao.mapper;

import com.nightingale.simplelearning.model.Answer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AnswerRowMapper implements RowMapper<Answer> {
    @Override
    public Answer mapRow(ResultSet rs, int rowNum) throws SQLException {

        Answer answer = new Answer();

        answer.setAnswerId(rs.getInt("answerId"));
        answer.setAnswer(rs.getString("answer"));

        return answer;
    }
}