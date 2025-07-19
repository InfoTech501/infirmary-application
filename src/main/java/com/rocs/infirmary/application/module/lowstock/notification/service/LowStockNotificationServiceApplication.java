package com.rocs.infirmary.application.module.lowstock.notification.service;

import com.rocs.infirmary.application.app.facade.dashboard.DashboardFacade;
import com.rocs.infirmary.application.app.facade.dashboard.impl.DashboardFacadeImpl;
import com.rocs.infirmary.application.data.dao.report.dashboard.DashboardReports;
import com.rocs.infirmary.application.data.dao.report.dashboard.impl.DashboardReportsDaoImpl;
/**
 * It sets up the components needed to show low stock notifications.
 * It creates a DashboardFacade using the DashboardReportsDao, which is used
 * to get information about products that are low in stock.
 */
public class LowStockNotificationServiceApplication  {

    private DashboardFacade dashboardFacade;
    /**
     *  Initializes the LowStockNotificationServiceApplication by setting up the
     *   dashboard data access and facade layers.
     */
    public LowStockNotificationServiceApplication() {

        DashboardReports dashboardReports = new DashboardReportsDaoImpl();
        this.dashboardFacade = new DashboardFacadeImpl(dashboardReports);

    }
    /**
     * Gets the Dashboard facade.
     *
     * @return the Dashboard facade.
     */
    public DashboardFacade getDashboardFacade () {
            return dashboardFacade;
    }
}