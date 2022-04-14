package com.nightingale.simplelearning.dao.mapper;

import com.nightingale.simplelearning.model.Course;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CourseListRowMapper implements  RowMapper<Course>{
    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {

        Course course = new Course();

        course.setCourseId(rs.getInt("courseId"));
        course.setTitle(rs.getString("title"));
        course.setDescription(rs.getString("description"));

        return course;
    }
}
