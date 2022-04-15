package com.nightingale.simplelearning.service.impl;

import com.nightingale.simplelearning.dao.MaterialDAO;
import com.nightingale.simplelearning.model.Material;
import com.nightingale.simplelearning.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialDAO materialDAO;

    @Override
    public Material getMaterialById(BigInteger id) {
        return materialDAO.getMaterialById(id);
    }

    @Override
    public List<Material> getAllMaterialsByCourseId(BigInteger courseId) {
        return materialDAO.getAllMaterialsByCourseId(courseId);
    }

    @Override
    public List<Material> getAllMaterials() {
        return materialDAO.getAllMaterials();
    }

    @Override
    public boolean save(Material material, BigInteger courseId) {
        return materialDAO.save(material, courseId);
    }

    @Override
    public boolean delete(BigInteger id) {
        return materialDAO.delete(id);
    }

    @Override
    public boolean update(BigInteger id, Material newMaterial) {
        return materialDAO.update(id, newMaterial);
    }
}
