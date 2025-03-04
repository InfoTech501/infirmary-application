package com.rocs.medical.records.application.app.facade.frequentVisitReport.impl;

import com.rocs.medical.records.application.app.facade.frequentVisitReport.FrequentVisitReportFacade;
import com.rocs.medical.records.application.data.dao.frequentVisitReport.FrequentVisitReportDao;
import com.rocs.medical.records.application.data.dao.frequentVisitReport.impl.FrequentVisitReportDaoImpl;
import com.rocs.medical.records.application.model.reports.FrequentVisitReport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FrequentVisitReportFacadeImpl implements FrequentVisitReportFacade {

    /** This is the data access object for the FrequentVisitReport. */
    private FrequentVisitReportDao frequentVisitReportDao = new FrequentVisitReportDaoImpl();

    /**
     * * This generates the report of Frequent visit within the given report period.
     * * @param startDate -  The start of the report date period.
     * * @param endDate - The end of the report date period.
     * * @param gradeLevel - The grade level of the students with frequent visit.
     * * @return - Return the list of frequent visit report.
     * */
    @Override
    public List<FrequentVisitReport> generateReport(Date startDate, Date endDate, String gradeLevel) {
        return frequentVisitReportDao.getFrequentVisitReports(gradeLevel, startDate, endDate);
    }

    /**
     * *This handles displaying of the report within the given report period.
     * * @param reports - Checking is there is a report within the given report period.
     * * @param startDate -  The start of the report date period.
     * * @param endDate - The end of the report date period.
     * * @param gradeLevel - The grade level of the students with frequent visit.
     */
    @Override
    public void handleFrequentVisit(List<FrequentVisitReport> reports, Date startDate, Date endDate, String gradeLevel) {
        if (reports == null || reports.isEmpty()) {
            System.out.println("No data available for the selected criteria.");
            return;
        }
        SimpleDateFormat displayFormat = new SimpleDateFormat("MMMM dd, yyyy");
        System.out.println("Frequent Visit Report");
        System.out.println("Period of Date: " + displayFormat.format(startDate) + " to " + displayFormat.format(endDate));
        System.out.println("Total no. of Visit: " + reports.size());

        for (FrequentVisitReport report : reports) {
            printFrequentVisit(report);
        }
    }

    /**
     * * This prints the details of frequent visit report of the students.
     * * @param report  - The frequent visit report to be printed.
     * */
    private static void printFrequentVisit(FrequentVisitReport report) {
        System.out.println("\nStudent Id: " + report.getStudentId());
        System.out.println("\nStudent First Name: " + report.getFirstName());
        System.out.println("\nStudent Last Name: " + report.getLastName());
        System.out.println("\nVisit Date: " + report.getVisitDate());
        System.out.println("\nGrade Level: " + report.getGradeLevel());
        System.out.println("\nHealth Concern: " + report.getSymptoms());
        System.out.println("\nTotal Visit: " + report.getVisitCount());
    }
}
