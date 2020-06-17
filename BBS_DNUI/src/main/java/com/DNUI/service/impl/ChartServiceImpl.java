package com.DNUI.service.impl;

import com.DNUI.dao.ITopicDao;
import com.DNUI.domain.Type;
import com.DNUI.service.IChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("chartService")
public class ChartServiceImpl implements IChartService {
    private final ITopicDao topicDao;
    @Autowired
    public ChartServiceImpl(ITopicDao topicDao) {
        this.topicDao = topicDao;
    }

    @Override
    public List<Map<String,Integer>> typeBarChart() {
        List<Type> types = topicDao.findAllType();
        List<Map<String,Integer>> typeList = new ArrayList<Map<String, Integer>>();
        for (Type type:types){
            Map<String,Integer> typeNum = new HashMap<String, Integer>();
            typeNum.put(type.getType_name(),topicDao.findTopicNumOfType(type.getType_name()));
            typeList.add(typeNum);
        }
        System.out.println(typeList);
        return typeList;
    }

    @Override
    public List<Map<String,Object>> angleChart() {
        List<Type> types = topicDao.findAllType();
        List<Map<String,Object>> typeList = new ArrayList<Map<String, Object>>();
        for (Type type:types){
            Map<String,Object> typeNum = new HashMap<String, Object>();
            typeNum.put("value",topicDao.findTopicNumOfType(type.getType_name()));
            typeNum.put("name",type.getType_name());
            typeList.add(typeNum);
        }
        return typeList;
    }
}
