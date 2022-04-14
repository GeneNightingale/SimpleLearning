package com.nightingale.simplelearning.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public class Page {

    private long pageId;

    private long pageNum;

    private String text;

    public long getPageId() {
        return pageId;
    }

    public void setPageId(long pageId) {
        this.pageId = pageId;
    }

    public long getPageNum() {
        return pageNum;
    }

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Page page = (Page) o;
        return Objects.equals(getPageId(), page.getPageId()) &&
                Objects.equals(getPageNum(), page.getPageNum()) &&
                Objects.equals(getText(), page.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPageId(), getPageNum(), getText());
    }
}
