package com.rocs.infirmary.application.module.lowstock.notification.service;

import com.rocs.infirmary.application.app.facade.dashboard.DashboardFacade;
import com.rocs.infirmary.application.app.facade.dashboard.impl.DashboardFacadeImpl;
import com.rocs.infirmary.application.data.dao.report.dashboard.DashboardReportsDao;
import com.rocs.infirmary.application.data.dao.report.dashboard.impl.DashboardReportsDaoImpl;


public class LowStockNotificationServiceApplication  {

    private DashboardFacade dashboardFacade;

    public LowStockNotificationServiceApplication() {

        DashboardReportsDao dashboardReports = new DashboardReportsDaoImpl();
        this.dashboardFacade = new DashboardFacadeImpl(dashboardReports);

    }

    public DashboardFacade getDashboardFacade () {
            return dashboardFacade;
    }


}
