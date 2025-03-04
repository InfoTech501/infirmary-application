package com.rocs.medical.records.application.app.facade.frequentVisitReport.impl;

import com.rocs.medical.records.application.app.facade.frequentVisitReport.FrequentVisitReportFacade;
import com.rocs.medical.records.application.data.dao.frequentVisitReport.FrequentVisitReportDao;
import com.rocs.medical.records.application.data.dao.frequentVisitReport.impl.FrequentVisitReportDaoImpl;
import com.rocs.medical.records.application.model.reports.FrequentVisitReport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FrequentVisitReportFacadeImpl implements FrequentVisitReportFacade {

    private FrequentVisitReportDao frequentVisitReportDao = new FrequentVisitReportDaoImpl();

    @Override
    public List<FrequentVisitReport> generateReport(Date startDate, Date endDate, String gradeLevel) {
        return frequentVisitReportDao.getFrequentVisitReports(gradeLevel, startDate, endDate);
    }

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
