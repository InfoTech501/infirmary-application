package com.rocs.infirmary.application.module.dashboard.information.application;

import com.rocs.infirmary.application.app.facade.dashboard.DashboardFacade;
import com.rocs.infirmary.application.app.facade.dashboard.impl.DashboardFacadeImpl;
import com.rocs.infirmary.application.data.dao.report.dashboard.DashboardReports;
import com.rocs.infirmary.application.data.dao.report.dashboard.impl.DashboardReportsImpl;

public class DashboardInfoApplication {
    private DashboardFacade dashboardFacade;
    /**
     * This creates a new DashboardInfoApplication
     * @return the dashboardFacade this helps for managing dashboard reports.
     */
    public DashboardInfoApplication() {
        DashboardReports dashboardReportsDao = new DashboardReportsImpl();
        this.dashboardFacade = new DashboardFacadeImpl();
    }
    /**
     * This gets the Dashboard Facade.
     * @return the dashboard facade.
     */
    public DashboardFacade getDashboardFacade() {
        return  dashboardFacade;
    }
}