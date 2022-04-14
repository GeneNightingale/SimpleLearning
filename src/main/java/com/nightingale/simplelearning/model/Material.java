package com.nightingale.simplelearning.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public class Material {

    private long materialId;

    private String title;

    private String link;

    public long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(long materialId) {
        this.materialId = materialId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Material material = (Material) o;
        return Objects.equals(getMaterialId(), material.getMaterialId()) &&
                Objects.equals(getTitle(), material.getTitle()) &&
                Objects.equals(getLink(), material.getLink());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMaterialId(), getTitle(), getLink());
    }
}
