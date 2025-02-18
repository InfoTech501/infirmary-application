package com.rocs.medical.records.application.app.facade.frequentVisitReport.impl;

import com.rocs.medical.records.application.app.facade.frequentVisitReport.FrequentVisitReportFacade;
import com.rocs.medical.records.application.data.dao.frequentVisitReport.FrequentVisitReportDao;
import com.rocs.medical.records.application.data.dao.frequentVisitReport.impl.FrequentVisitReportDaoImpl;
import com.rocs.medical.records.application.model.reports.FrequentVisitReport;

<<<<<<< HEAD
=======
import java.text.SimpleDateFormat;
>>>>>>> cec7215 (frequent visit update)
import java.util.Date;
import java.util.List;

public class FrequentVisitReportFacadeImpl implements FrequentVisitReportFacade {
    private FrequentVisitReportDao frequentVisitDao = new FrequentVisitReportDaoImpl();

    @Override
    public List<FrequentVisitReport> generateReport(Date startDate, Date endDate, String gradeLevel) {
        return frequentVisitDao.getFrequentVisitReports(gradeLevel, startDate, endDate);
    }
<<<<<<< HEAD
}

=======

    @Override
    public void handleFrequentVisit(List<FrequentVisitReport> reports, Date startDate, Date endDate, String gradeLevel) {
        if (reports == null || reports.isEmpty()) {
            System.out.println("No data available for the selected criteria.");
            return;
        }
        SimpleDateFormat displayFormat = new SimpleDateFormat("MMMM dd, yyyy");
        System.out.println("Frequent Visit Report");
        System.out.println("Period of Date: " + displayFormat.format(startDate) + " to " + displayFormat.format(endDate));
        System.out.println("Grade level: " + (gradeLevel != null ? gradeLevel : "ALL"));

        for (FrequentVisitReport report : reports) {
            printFrequentVisit(report);
        }
    }

    private void printFrequentVisit(FrequentVisitReport report) {
        System.out.println("Student Id: " + report.getStudentId());
        System.out.println("Student First Name: " + report.getFirstName());
        System.out.println("Student Last Name: " + report.getLastName());
        System.out.println("Visit Date: " + report.getVisitDate());
        System.out.println("Grade Level: " + report.getGradeLevel());
        System.out.println("Health Concern: " + report.getSymptoms());
    }
}
>>>>>>> cec7215 (frequent visit update)
