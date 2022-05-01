package com.nightingale.simplelearning.dao;

import com.nightingale.simplelearning.model.Material;

import java.math.BigInteger;
import java.util.List;

public interface MaterialDAO {

    static final String SELECT_MATERIAL_BY_ID = "SELECT * FROM material " +
            "WHERE materialId = ?";

    static final String SELECT_MATERIALS_BY_COURSE_ID = "SELECT * FROM material " +
            "WHERE courseId = ?";

    static final String SELECT_MATERIALS_BY_COURSE_ID_STUDENT = "SELECT * FROM material " +
            "WHERE isPublic = 'true' AND courseId = ?";

    static final String SELECT_ALL_MATERIALS = "SELECT * FROM material";

    static final String INSERT_MATERIAL_TO_COURSE = "INSERT INTO material (title, courseId, link)" +
            "VALUES(?,?,?)";

    static final String DELETE_MATERIAL_BY_ID = "DELETE FROM material WHERE materialId = ?";

    static final String DELETE_ALL_MATERIALS_BY_COURSE_ID = "DELETE FROM material WHERE courseId = ?";

    static final String UPDATE_MATERIAL_BY_ID = "UPDATE material " +
            "SET title = ?, link = ? " +
            "WHERE materialId = ?;";

    String MAKE_PUBLIC = "UPDATE material SET isPublic = 'true' WHERE materialId = ?;";

    String MAKE_PRIVATE = "UPDATE material SET isPublic = 'false' WHERE materialId = ?;";

    Material getMaterialById(BigInteger id);
    List<Material> getAllMaterialsByCourseId(BigInteger courseId);
    List<Material> getAllMaterialsByCourseIdStudent(BigInteger courseId);
    List<Material> getAllMaterials();
    boolean deleteAllMaterialsByCourseId(BigInteger id);
    boolean makePublic(BigInteger materialId);
    boolean makePrivate(BigInteger materialId);
    boolean save(Material material, BigInteger courseId);
    boolean delete(BigInteger id);
    boolean update(BigInteger id, Material newMaterial);
}
