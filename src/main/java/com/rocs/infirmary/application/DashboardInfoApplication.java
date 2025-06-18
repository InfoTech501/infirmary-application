package com.rocs.infirmary.application;

import com.rocs.infirmary.application.app.facade.dashboard.DashboardFacade;
import com.rocs.infirmary.application.app.facade.dashboard.impl.DashboardFacadeImpl;
import com.rocs.infirmary.application.data.dao.report.dashboard.DashboardReports;
import com.rocs.infirmary.application.data.dao.report.dashboard.impl.DashboardReportsImpl;

public class DashboardInfoApplication {
    private DashboardFacade dashboardFacade;


    public DashboardInfoApplication() {
        DashboardReports dashboardReportsDao = new DashboardReportsImpl();
        this.dashboardFacade = new DashboardFacadeImpl();
    }
    public DashboardFacade getDashboardFacade() {
        return  dashboardFacade;
    }
}