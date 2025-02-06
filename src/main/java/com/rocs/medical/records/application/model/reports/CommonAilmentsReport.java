package com.rocs.medical.records.application.model.reports;

import com.rocs.medical.records.application.model.reportAffectedStudents.ReportAffectedStudents;

import java.util.Date;
import java.util.List;

public class CommonAilmentsReport {
    private Long id;
    private Long patientId;
    private Date reportDate;
    private String ailment;
    private int occurrences;
    private List<ReportAffectedStudents> mockAffectedStudents;

    public CommonAilmentsReport(
            Long id,
            Long patientId,
            Date reportDate,
            String ailment,
            int occurrences,
            List<ReportAffectedStudents> mockAffectedStudents
    ) {
        this.id = id;
        this.patientId = patientId;
        this.reportDate = reportDate;
        this.ailment = ailment;
        this.occurrences = occurrences;
        this.mockAffectedStudents = mockAffectedStudents;
    }

    public CommonAilmentsReport() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getAilment() {
        return ailment;
    }

    public void setAilment(String ailment) {
        this.ailment = ailment;
    }

    public int getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }

    public List<ReportAffectedStudents> getAffectedStudents() {
        return mockAffectedStudents;
    }

    public void setReportAffectedStudents(List<ReportAffectedStudents> mockAffectedStudents) {
        this.mockAffectedStudents = mockAffectedStudents;
    }

    @Override
    public String toString() {
        return "CommonAilmentsReport{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", reportDate=" + reportDate +
                ", ailment='" + ailment + '\'' +
                ", occurrences=" + occurrences +
                ", mockAffectedStudents=" + mockAffectedStudents +
                '}';
    }
}
