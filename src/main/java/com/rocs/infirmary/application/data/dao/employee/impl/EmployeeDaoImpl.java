package com.rocs.infirmary.application.data.dao.employee.impl;

import com.rocs.infirmary.application.data.connection.ConnectionHelper;
import static com.rocs.infirmary.application.data.dao.utils.queryconstants.employee.QueryConstants.*;

import com.rocs.infirmary.application.data.dao.employee.EmployeeDao;
import com.rocs.infirmary.application.data.dao.student.record.impl.StudentMedicalRecordDaoImpl;
import com.rocs.infirmary.application.data.model.person.employee.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The EmployeeDaoImpl class implements the EmployeeDao interface
 * it provides methods for interacting with the infirmary database.
 * It includes methods for retrieving employee information.
 */
public class EmployeeDaoImpl implements EmployeeDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentMedicalRecordDaoImpl.class);

    @Override
    public List<Employee> getAllNurseAccounts() {
        List<Employee> nurses = new ArrayList<>();

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_ALL_NURSE_EMPLOYEE);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                try {
                    Employee nurse = new Employee();
                    nurse.setNurseInChargeId(rs.getLong("id"));
                    nurse.setFirstName(rs.getString("first_name"));
                    nurse.setMiddleName(rs.getString("middle_name"));
                    nurse.setLastName(rs.getString("last_name"));

                    String fullName = nurse.getFirstName() + " " + nurse.getLastName();
                    nurse.setNurseInCharge(fullName.trim());

                    nurses.add(nurse);
                    LOGGER.info("Mapped Nurse: {}", fullName.trim());
                } catch (Exception ex) {
                    LOGGER.warn("Error mapping nurse record. Row skipped: {}", ex.getMessage());
                }
            }

        } catch (SQLException e) {
            LOGGER.error("SQLException Occurred: {}", e.getMessage(), e);
            throw new RuntimeException("Error fetching nurse accounts", e);
        }

        return nurses;
    }
}
