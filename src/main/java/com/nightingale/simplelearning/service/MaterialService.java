package com.nightingale.simplelearning.service;

import com.nightingale.simplelearning.model.Material;

import java.math.BigInteger;
import java.util.List;

public interface MaterialService {
    Material getMaterialById(BigInteger id);
    List<Material> getAllMaterialsByCourseId(BigInteger courseId);
    List<Material> getAllMaterials();
    boolean makePublic(BigInteger materialId);
    boolean makePrivate(BigInteger materialId);
    boolean save(Material material, BigInteger courseId);
    boolean delete(BigInteger id);
    boolean update(BigInteger id, Material newMaterial);
}
