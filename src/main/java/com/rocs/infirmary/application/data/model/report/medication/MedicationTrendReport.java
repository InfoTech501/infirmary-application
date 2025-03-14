package com.rocs.infirmary.application.data.model.report.medication;

import com.rocs.infirmary.application.data.model.report.Report;

public class MedicationTrendReport extends Report {

    private int usage;
    private String medicineName;
    private int stocks;

    public MedicationTrendReport( String medicineName, int usage, int stocks) {
        this.usage = usage;
        this.medicineName = medicineName;
        this.stocks = stocks;
    }

    public MedicationTrendReport() {
    }

    public int getUsage() {
        return usage;
    }

    public void setUsage(int usage) {
        this.usage = usage;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getStocks() {
        return stocks;
    }

    public void setStocks(int stocks) {
        this.stocks = stocks;
    }

    @Override
    public String toString() {
        return "MedicationTrendsReport{" +
                ", medicineName='" + medicineName + '\'' +
                ", usage=" + usage +
                ", stocks=" + stocks +
                '}';
    }

}
