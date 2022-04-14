package com.nightingale.simplelearning.dao.mapper;

import com.nightingale.simplelearning.model.Page;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PageRowMapper implements RowMapper<Page> {
    @Override
    public Page mapRow(ResultSet rs, int rowNum) throws SQLException {

        Page page = new Page();

        page.setPageId(rs.getInt("pageId"));
        page.setPageNum(rs.getInt("pageNum"));
        page.setText(rs.getString("text"));

        return page;

    }
}