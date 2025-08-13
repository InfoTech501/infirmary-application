package com.rocs.infirmary.application.data.dao.employee;

import com.rocs.infirmary.application.data.model.person.employee.Employee;

import java.util.List;
/**
 * {@code EmployeeDao} is used to facilitate employee-information-related operations within the system.
 * this handles the business logic for retrieving employee information.
 **/
public interface EmployeeDao {

    /**
     * Retrieves all nurse information from the database.
     * @return A list of nurse information, or an empty list if no records are found.
     */
    List<Employee> getAllNurseAccounts();
}
