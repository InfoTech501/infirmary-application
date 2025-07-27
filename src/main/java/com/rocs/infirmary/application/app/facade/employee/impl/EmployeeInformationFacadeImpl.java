package com.rocs.infirmary.application.app.facade.employee.impl;

import com.rocs.infirmary.application.app.facade.employee.EmployeeInformationFacade;
import com.rocs.infirmary.application.data.dao.employee.EmployeeDao;
import com.rocs.infirmary.application.data.model.person.employee.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EmployeeInformationFacadeImpl implements EmployeeInformationFacade {

    private EmployeeDao employeeDao;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeInformationFacadeImpl.class);

    public EmployeeInformationFacadeImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public List<Employee> getAllNurseAccounts() {
        logger.info("Retrieving all nurse accounts...");
        List<Employee> nurses = employeeDao.getAllNurseAccounts();
        logger.info("Retrieved {} nurse records", nurses.size());
        return nurses;
    }
}
