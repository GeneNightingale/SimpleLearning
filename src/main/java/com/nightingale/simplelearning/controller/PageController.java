package com.nightingale.simplelearning.controller;

import com.nightingale.simplelearning.model.Page;
import com.nightingale.simplelearning.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping(value = "/api/page")
public class PageController {

    @Autowired
    private PageService pageService;

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/by_lecture/{lecture_id}")
    public ResponseEntity<?> addPage(@Valid @RequestBody Page page, @PathVariable("lecture_id") BigInteger id) {
        pageService.save(page, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public List<Page> getAllPages() {
        return pageService.getAllPages();
    }

    @GetMapping("/{page_id}")
    public Page getPage(@PathVariable("page_id") BigInteger id) {
        return pageService.getPageById(id);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/{page_id}")
    public ResponseEntity<?> updatePage(@Valid @RequestBody Page page, @PathVariable("page_id") BigInteger id) {
        pageService.update(id, page);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/{page_id}")
    public boolean deletePage(@PathVariable("page_id") BigInteger id) {
        return pageService.delete(id);
    }

    @GetMapping("/by_lecture/{lecture_id}")
    public List<Page> getPagesByLectureId(@PathVariable("lecture_id") BigInteger id) {
        return pageService.getAllPagesByLectureId(id);
    }
}
