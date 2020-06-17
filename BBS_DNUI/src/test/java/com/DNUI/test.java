package com.DNUI;

import com.DNUI.controller.TopicController;
import com.DNUI.dao.ITopicDao;
import com.DNUI.service.*;
import com.DNUI.utils.SensitiveWordsUtil;
import com.fasterxml.jackson.annotation.JsonAlias;
import org.json.JSONArray;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

public class test {
    @Test
    public void test01(){
        //加载配置文件
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        //获取对象
        IChartService as = (IChartService) ac.getBean("chartService");
        //调用方法
        System.out.println(as.angleChart());


    }
}
