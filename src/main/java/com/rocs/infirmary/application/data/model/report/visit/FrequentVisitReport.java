package com.rocs.infirmary.application.data.model.report.visit;

import com.rocs.infirmary.application.data.model.report.Report;

public class FrequentVisitReport extends Report {
    private int visitCount;

    public int getVisitCount() { return visitCount; }

    public void setVisitCount(int visitCount) { this.visitCount = visitCount; }

}
