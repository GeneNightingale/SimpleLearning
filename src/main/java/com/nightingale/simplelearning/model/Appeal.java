package com.nightingale.simplelearning.model;

import com.nightingale.simplelearning.model.enums.Status;

import java.util.Objects;

@Deprecated
public class Appeal {

    private long appealId;

    private User user;

    private Status status;

    private String text;

    private String reasonForDenial;

    public long getAppealId() {
        return appealId;
    }

    public void setAppealId(long appealId) {
        this.appealId = appealId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReasonForDenial() {
        return reasonForDenial;
    }

    public void setReasonForDenial(String reasonForDenial) {
        this.reasonForDenial = reasonForDenial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Appeal appeal = (Appeal) o;
        return Objects.equals(getAppealId(), appeal.getAppealId()) &&
                Objects.equals(getUser(), appeal.getUser()) &&
                Objects.equals(getStatus(), appeal.getStatus()) &&
                Objects.equals(getText(), appeal.getText()) &&
                Objects.equals(getReasonForDenial(), appeal.getReasonForDenial());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAppealId(), getUser(), getStatus(), getText(), getReasonForDenial());
    }
}
