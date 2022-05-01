package com.nightingale.simplelearning.dao.mapper;

import com.nightingale.simplelearning.model.Material;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MaterialRowMapper implements RowMapper<Material> {
    @Override
    public Material mapRow(ResultSet rs, int rowNum) throws SQLException {

        Material material = new Material();

        material.setMaterialId(rs.getInt("materialId"));
        material.setTitle(rs.getString("title"));
        material.setLink(rs.getString("link"));
        material.setPublic(Boolean.parseBoolean(rs.getString("isPublic")));
        return material;

    }
}
