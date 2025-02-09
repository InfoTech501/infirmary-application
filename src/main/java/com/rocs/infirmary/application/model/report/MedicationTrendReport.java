package com.rocs.infirmary.application.model.report;

import java.util.Date;

public class MedicationTrendReport {

    private int id;
    private Date startDate;
    private Date endDate;
    private String medName;
    private int quantityUsed;
    private int quantityAvailable;

    public MedicationTrendReport(int id, Date startDate, Date endDate, String medName, int quantityUsed, int quantityAvailable) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.medName = medName;
        this.quantityUsed = quantityUsed;
        this.quantityAvailable = quantityAvailable;
    }

    public MedicationTrendReport() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startingDate) {
        this.startDate = startingDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public int getQuantityUsed() {
        return quantityUsed;
    }

    public void setQuantityUsed(int medTotalUsage) {
        this.quantityUsed = medTotalUsage;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    @Override
    public String toString() {
        return "MedicationTrendsReport{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", medName='" + medName + '\'' +
                ", quantityUsed=" + quantityUsed +
                ", quantityAvailable=" + quantityAvailable +
                '}';
    }

}
