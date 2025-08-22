package com.rocs.infirmary.application.module.lowstock.notification.service.application;

import com.rocs.infirmary.application.app.facade.dashboard.DashboardFacade;
import com.rocs.infirmary.application.app.facade.dashboard.impl.DashboardFacadeImpl;
import com.rocs.infirmary.application.data.dao.report.dashboard.DashboardReports;
import com.rocs.infirmary.application.data.dao.report.dashboard.impl.DashboardReportsImpl;
/**
 * It sets up the components needed to show low stock notifications.
 * It creates a DashboardFacade using the DashboardReportsDao, which is used
 * to get information about products that are low in stock.
 */
public class LowStockNotificationServiceApplication {

    private DashboardFacade dashboardFacade;
    /**
     * Creates the objects needed for dashboard reports
     * and connects them together.
     */
    public LowStockNotificationServiceApplication() {

        DashboardReports dashboardReports = new DashboardReportsImpl();
        this.dashboardFacade = new DashboardFacadeImpl(dashboardReports);

    }
    /**
     * Returns the dashboard facade object.
     *
     * @return dashboardFacade
     */
    public DashboardFacade getDashboardFacade () {
        return dashboardFacade;
    }
}