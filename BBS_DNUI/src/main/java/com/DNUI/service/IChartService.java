package com.DNUI.service;

import com.DNUI.domain.Type;

import java.util.List;
import java.util.Map;

public interface IChartService {
    public List<Map<String,Integer>> typeBarChart();
    public List<Map<String,Object>> angleChart();
}
