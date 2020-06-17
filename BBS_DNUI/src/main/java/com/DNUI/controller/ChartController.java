package com.DNUI.controller;

import com.DNUI.domain.Type;
import com.DNUI.service.IChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/chart")
public class ChartController {
    private final IChartService chartService;
    @Autowired
    public ChartController(IChartService chartService) {
        this.chartService = chartService;
    }

    @RequestMapping(value = "/typeBarChart")
    @ResponseBody
    public List<Map<String,Integer>> typeBarChart(){
        return chartService.typeBarChart();
    }
    @RequestMapping(value = "/typeAngleChart")
    @ResponseBody
    public List<Map<String,Object>> angleChart(){
        return chartService.angleChart();
    }
}
