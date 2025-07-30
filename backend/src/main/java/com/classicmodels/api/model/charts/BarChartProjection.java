package com.classicmodels.api.model.charts;

import com.classicmodels.domain.model.charts.BarChart;

import java.util.List;

public interface BarChartProjection {

    List<BarChart> findTopCountryProductSales();

}
