package com.classicmodels.api.controller;

import com.classicmodels.api.model.charts.PieChartProjection;
import com.classicmodels.domain.repository.charts.PieChartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/charts")
@CrossOrigin(origins = "${CONTROLLERS_CROSS_ORIGIN}")
public class ChartsController {

    @Autowired
    private PieChartRepository pieChartRepository;

    @GetMapping("/pie")
    public List<PieChartProjection> listar() {

        return pieChartRepository.findOrdersByCountry();
    }

}
