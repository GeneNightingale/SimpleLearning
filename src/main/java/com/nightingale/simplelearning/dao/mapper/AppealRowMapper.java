package com.nightingale.simplelearning.dao.mapper;

import com.nightingale.simplelearning.dao.UserDAO;
import com.nightingale.simplelearning.model.Appeal;
import com.nightingale.simplelearning.model.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AppealRowMapper implements RowMapper<Appeal> {

    @Autowired
    private UserDAO userDAO;

    @Override
    public Appeal mapRow(ResultSet rs, int rowNum) throws SQLException {

        Appeal appeal = new Appeal();

        appeal.setText(rs.getString("text"));
        appeal.setAppealId(rs.getInt("appealId"));
        appeal.setReasonForDenial(rs.getString("reasonForDenial"));

        switch (rs.getString("status")) {
            case "APPROVED":
                appeal.setStatus(Status.APPROVED);
                break;
            case "DENIED":
                appeal.setStatus(Status.DENIED);
                break;
        }

        int userId = rs.getInt("userId");
        if (userId >= 0)
            appeal.setUser(userDAO.getUserById(BigInteger.valueOf(userId)));

        return appeal;
    }
}
