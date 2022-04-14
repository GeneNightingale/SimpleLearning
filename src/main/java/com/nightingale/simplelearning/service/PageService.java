package com.nightingale.simplelearning.service;

import com.nightingale.simplelearning.model.Page;

import java.math.BigInteger;
import java.util.List;

public interface PageService {
    Page getPageById(BigInteger id);
    List<Page> getAllPagesByLectureId(BigInteger lectureId);
    List<Page> getAllPages();
    boolean save(Page page, BigInteger lectureId);
    boolean delete(BigInteger id);
    boolean update(BigInteger id, Page newPage);
}
