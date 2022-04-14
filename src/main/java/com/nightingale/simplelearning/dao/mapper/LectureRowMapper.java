package com.nightingale.simplelearning.dao.mapper;

import com.nightingale.simplelearning.model.Lecture;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LectureRowMapper implements RowMapper<Lecture> {
    @Override
    public Lecture mapRow(ResultSet rs, int rowNum) throws SQLException {

        Lecture lecture = new Lecture();

        lecture.setLectureId(rs.getInt("lectureId"));
        lecture.setTitle(rs.getString("title"));
        //TODO: DO SOMETHING ABOUT THE PAGES

        return lecture;

    }
}
