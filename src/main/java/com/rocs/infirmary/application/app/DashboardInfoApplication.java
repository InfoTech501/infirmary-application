package com.rocs.infirmary.application.app;

import com.rocs.infirmary.application.app.facade.dashboard.DashboardFacade;
import com.rocs.infirmary.application.app.facade.dashboard.impl.DashboardFacadeImpl;
import com.rocs.infirmary.application.data.dao.report.dashboard.DashboardReports;
import com.rocs.infirmary.application.data.dao.report.dashboard.impl.DashboardReportsImpl;

public class DashboardInfoApplication {
    private DashboardFacade dashboardFacade;
    private DashboardReports dashboardReportsDao = new DashboardReportsImpl();

    public DashboardInfoApplication() {
        this.dashboardFacade = new DashboardFacadeImpl(dashboardReportsDao);
    }
    public DashboardFacade getDashboardFacade() {
        return  dashboardFacade;
    }
}