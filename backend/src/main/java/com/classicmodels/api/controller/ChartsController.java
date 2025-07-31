package com.classicmodels.api.controller;

import com.classicmodels.api.model.charts.PieChartProjection;
import com.classicmodels.domain.model.charts.BarChart;
import com.classicmodels.domain.repository.charts.BarChartRepository;
import com.classicmodels.domain.repository.charts.PieChartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/charts")
@CrossOrigin(origins = "${CONTROLLERS_CROSS_ORIGIN}")
public class ChartsController {

    @Autowired
    private final PieChartRepository pieChartRepository;

    @Autowired
    private final BarChartRepository barChartRepository;

    @GetMapping("/pie")
    public List<PieChartProjection> listar() {

        return pieChartRepository.findOrdersByCountry();
    }

    @GetMapping("/bar")
    private List<BarChart> listarBarChart() {
        return barChartRepository.findTopCountryProductSales();
    }

}
