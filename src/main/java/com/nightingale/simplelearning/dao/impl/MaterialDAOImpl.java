package com.nightingale.simplelearning.dao.impl;

import com.nightingale.simplelearning.dao.CourseDAO;
import com.nightingale.simplelearning.dao.MaterialDAO;
import com.nightingale.simplelearning.dao.mapper.CourseListRowMapper;
import com.nightingale.simplelearning.dao.mapper.MaterialRowMapper;
import com.nightingale.simplelearning.model.Course;
import com.nightingale.simplelearning.model.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class MaterialDAOImpl implements MaterialDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MaterialRowMapper materialRowMapper;

    @Autowired
    private CourseDAO courseDAO;

    @Autowired
    private CourseListRowMapper courseListRowMapper;

    private static final Logger LOGGER = Logger.getLogger(MaterialDAOImpl.class.getName());

    @Override
    public Material getMaterialById(BigInteger id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_MATERIAL_BY_ID, materialRowMapper, id);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    public List<Material> getAllMaterialsByCourseId(BigInteger courseId) {
        try {
            return jdbcTemplate.query(SELECT_MATERIALS_BY_COURSE_ID, materialRowMapper, courseId);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    public List<Material> getAllMaterials() {
        try {
            return jdbcTemplate.query(SELECT_ALL_MATERIALS, materialRowMapper);
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class)
    public boolean deleteAllMaterialsByCourseId(BigInteger id) {
        try {
            Course course = jdbcTemplate.queryForObject(courseDAO.SELECT_COURSE_BY_ID, courseListRowMapper, id);
            if (course!=null) {
                jdbcTemplate.update(DELETE_ALL_MATERIALS_BY_COURSE_ID, id);
                return true;
            } else {
                LOGGER.log(Level.WARNING, "No Course with given ID");
                return false;
            }
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = {DataAccessException.class})
    public boolean save(Material material, BigInteger courseId) {
        if(isInvalidMaterial(material)){
            System.out.println(material.getTitle());
            System.out.println(material.getLink());
            LOGGER.log(Level.WARNING, "Invalid Material");
            return false;
        }
        try {
            jdbcTemplate.update(INSERT_MATERIAL_TO_COURSE, material.getTitle(), courseId, material.getLink());
            return true;
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class)
    public boolean delete(BigInteger id) {
        try {
            Material material = jdbcTemplate.queryForObject(SELECT_MATERIAL_BY_ID, materialRowMapper, id);
            if (material!=null) {
                jdbcTemplate.update(DELETE_MATERIAL_BY_ID, id);
                return true;
            } else {
                LOGGER.log(Level.WARNING, "No Material with given ID");
                return false;
            }
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class)
    public boolean update(BigInteger id, Material newMaterial) {
        if(isInvalidMaterial(newMaterial)){
            LOGGER.log(Level.WARNING, "Invalid Material");
            return false;
        }
        try {
            Material material = jdbcTemplate.queryForObject(SELECT_MATERIAL_BY_ID, materialRowMapper, id);
            if (material!=null) {
                jdbcTemplate.update(UPDATE_MATERIAL_BY_ID, newMaterial.getTitle(),
                                                           newMaterial.getLink(), id);
                return true;
            } else {
                LOGGER.log(Level.WARNING, "No Material with given ID");
                return false;
            }
        } catch (DataAccessException dataAccessException) {
            LOGGER.log(Level.WARNING, dataAccessException.getMessage(), dataAccessException);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    private boolean isInvalidMaterial(Material material) {
        if (material == null)
            return true;
        else
            return material.getTitle() == null && material.getLink() == null;
    }

}
