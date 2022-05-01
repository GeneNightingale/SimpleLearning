package com.nightingale.simplelearning.dao.mapper;

import com.nightingale.simplelearning.model.Question;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class QuestionRowMapper implements RowMapper<Question> {
    @Override
    public Question mapRow(ResultSet rs, int rowNum) throws SQLException {

        Question question = new Question();

        question.setQuestionId(rs.getInt("questionId"));
        question.setQuestionNum(rs.getInt("questionNum"));
        question.setText(rs.getString("text"));
        question.setAnswer(rs.getString("answer"));

        return question;

    }
}