package com.nightingale.simplelearning.dao;

import com.nightingale.simplelearning.model.Material;
import com.nightingale.simplelearning.model.Page;

import java.math.BigInteger;
import java.util.List;

public interface PageDAO {

    static final String SELECT_PAGE_BY_ID = "SELECT * FROM page " +
            "WHERE pageId = ?";

    static final String SELECT_PAGES_BY_LECTURE_ID = "SELECT * FROM page " +
            "WHERE lectureId = ? " +
            "ORDER BY pageNum ASC;";

    static final String SELECT_ALL_PAGES = "SELECT * FROM page";

    static final String INSERT_PAGE_TO_LECTURE = "INSERT INTO page (pageNum, text, lectureId) " +
            "VALUES (?,?,?)";

    static final String DELETE_PAGE_BY_ID = "DELETE FROM page " +
            "WHERE pageId = ?";

    static final String DELETE_PAGES_BY_LECTURE_ID = "DELETE FROM page " +
            "WHERE lectureId = ?";

    static final String UPDATE_PAGE_BY_ID = "UPDATE page " +
            "SET pageNum = ?, text = ? " +
            "WHERE pageId = ?;";

    Page getPageById(BigInteger id);
    List<Page> getAllPagesByLectureId(BigInteger lectureId);
    List<Page> getAllPages();
    boolean deleteAllPagesByLectureId(BigInteger id);
    boolean save(Page page, BigInteger lectureId);
    boolean delete(BigInteger id);
    boolean update(BigInteger id, Page newPage);
}
