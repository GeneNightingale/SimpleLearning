package com.nightingale.simplelearning.controller;

import com.nightingale.simplelearning.model.Material;
import com.nightingale.simplelearning.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping(value = "/api/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/by_course/{course_id}")
    public ResponseEntity<?> addMaterial(@Valid @RequestBody Material material, @PathVariable("course_id") BigInteger id) {
        materialService.save(material, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public List<Material> getAllMaterials() {
        return materialService.getAllMaterials();
    }

    @GetMapping("/{material_id}")
    public Material getMaterial(@PathVariable("material_id") BigInteger id) {
        return materialService.getMaterialById(id);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/make_public/{material_id}")
    public ResponseEntity<?> makePublic(@PathVariable("material_id") BigInteger materialId) {
        materialService.makePublic(materialId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/make_private/{material_id}")
    public ResponseEntity<?> makePrivate(@PathVariable("material_id") BigInteger materialId) {
        materialService.makePrivate(materialId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/{material_id}")
    public ResponseEntity<?> updateMaterial(@Valid @RequestBody Material material, @PathVariable("material_id") BigInteger id) {
        materialService.update(id, material);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/{material_id}")
    public boolean deleteMaterial(@PathVariable("material_id") BigInteger id) {
        return materialService.delete(id);
    }

    @GetMapping("/by_course/{course_id}")
    public List<Material> getMaterialsByCourseId(@PathVariable("course_id") BigInteger id) {
        return materialService.getAllMaterialsByCourseId(id);
    }
}
