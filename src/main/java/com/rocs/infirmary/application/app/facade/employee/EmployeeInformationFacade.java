package com.rocs.infirmary.application.app.facade.employee;

import com.rocs.infirmary.application.data.model.person.employee.Employee;

import java.util.List;

public interface EmployeeInformationFacade {

    /**
     * Retrieves all nurse information from the database.
     *
     * @return A list of nurse information, or an empty list if no records are found.
     */
    List<Employee> getAllNurseAccounts();
}
