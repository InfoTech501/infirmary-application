package com.rocs.frequent.visit.function.data.facade.impl;

import com.rocs.frequent.visit.function.data.FreqVisitDao.FrequentVisitDao;
import com.rocs.frequent.visit.function.data.FreqVisitDao.impl.FrequentVisitDaoImpl;
import com.rocs.frequent.visit.function.data.facade.FrequentVisitFacade;
import com.rocs.frequent.visit.function.model.report.Patients;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

public class FrequentVisitFacadeImpl implements FrequentVisitFacade {
    private FrequentVisitDao dao;

    public FrequentVisitFacadeImpl() {
        this.dao = new FrequentVisitDaoImpl();
    }

    @Override
    public List<Patients> generateFrequentVisitReport(String gradeLevel, String startDate, String endDate) {
        return dao.getFrequentVisitors(gradeLevel, startDate, endDate);
    }
}