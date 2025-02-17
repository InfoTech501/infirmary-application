package com.rocs.medical.records.application.app.facade.commonAilmentsReport.impl;

import com.rocs.medical.records.application.data.dao.commonAilmentsReport.CommonAilmentsReportDAO;
import com.rocs.medical.records.application.data.dao.commonAilmentsReport.impl.CommonAilmentsReportDaoImpl;
import com.rocs.medical.records.application.app.facade.commonAilmentsReport.CommonAilmentsReportFacade;
import com.rocs.medical.records.application.model.person.Person;
import com.rocs.medical.records.application.model.reports.CommonAilmentsReport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommonAilmentsReportFacadeImpl implements CommonAilmentsReportFacade {
    private final CommonAilmentsReportDAO ailmentsReportDAO = new CommonAilmentsReportDaoImpl();

    @Override
    public List<CommonAilmentsReport> generateReport(Date startDate, Date endDate, String gradeLevel, String section) {
        return this.ailmentsReportDAO.getGeneratedReport(startDate, endDate, gradeLevel, section);
    }

    @Override
    public void displayCommonAilmentsReport(List<CommonAilmentsReport> reports, Date startDate, Date endDate, String gradeLevel, String section) {
        if (reports == null || reports.isEmpty()) {
            System.out.println("No data available for the selected criteria.");
            return;
        }

        SimpleDateFormat displayFormat = new SimpleDateFormat("MMMM dd, yyyy");
        System.out.println("Common Ailments Report");
        System.out.println("Period: " + displayFormat.format(startDate) + " to " + displayFormat.format(endDate));
        System.out.println("Grade level: " + (gradeLevel != null ? gradeLevel : "ALL"));
        System.out.println("Section: " + (section != null ? section : "ALL"));

        for (CommonAilmentsReport report : reports) {
            printAilmentSection(report);
        }

        System.out.println("\nReport Summary");
        System.out.println("Total no. of different ailments: " + reports.size());

        int totalOccurrences = reports.stream().mapToInt(CommonAilmentsReport::getOccurrences).sum();
        System.out.println("Total no. of occurrences: " + totalOccurrences);
    }

    private static void printAilmentSection(CommonAilmentsReport report) {
        System.out.println("\nAffected students:");
        for (Person student : report.getAffectedPeople()) {
            System.out.println(student.getFirstName() + " " + student.getLastName());
        }

        System.out.println("Ailment: " + report.getAilment());
        System.out.println("No. of occurrences per ailment: " + report.getOccurrences());
        System.out.println("Grade Level: " + report.getGradeLevel());
        System.out.println("Strand: " + report.getStrand());

    }
}