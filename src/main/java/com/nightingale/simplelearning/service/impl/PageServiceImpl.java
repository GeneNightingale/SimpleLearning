package com.nightingale.simplelearning.service.impl;

import com.nightingale.simplelearning.dao.PageDAO;
import com.nightingale.simplelearning.model.Page;
import com.nightingale.simplelearning.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class PageServiceImpl implements PageService {

    private PageDAO pageDAO;

    @Autowired
    public void setPageDAO(PageDAO pageDAO) {
        this.pageDAO = pageDAO;
    }

    @Override
    public Page getPageById(BigInteger id) {
        return pageDAO.getPageById(id);
    }

    @Override
    public List<Page> getAllPagesByLectureId(BigInteger lectureId) {
        return pageDAO.getAllPagesByLectureId(lectureId);
    }

    @Override
    public List<Page> getAllPages() {
        return pageDAO.getAllPages();
    }

    @Override
    public boolean save(Page page, BigInteger lectureId) {
        return pageDAO.save(page, lectureId);
    }

    @Override
    public boolean delete(BigInteger id) {
        return pageDAO.delete(id);
    }

    @Override
    public boolean update(BigInteger id, Page newPage) {
        return pageDAO.update(id, newPage);
    }
}
