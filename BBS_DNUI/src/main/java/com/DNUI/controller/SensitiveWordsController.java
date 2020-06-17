package com.DNUI.controller;

import com.DNUI.service.impl.SensitiveWordsServiceImpl;
import com.DNUI.utils.SensitiveWordsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping(value = "sensitive")
public class SensitiveWordsController {
    private final SensitiveWordsServiceImpl sensitiveWordsService;
    @Autowired
    public SensitiveWordsController(SensitiveWordsServiceImpl sensitiveWordsService) {
        this.sensitiveWordsService = sensitiveWordsService;
    }
    @RequestMapping(value = "/validateContent")
    @ResponseBody
    public Map validateContent(HttpServletRequest request){
        System.out.println("成功调用controller");
        Map<Object,Object> map = new HashMap();
        String content = request.getParameter("content").trim();
        System.out.println("sensitive:"+content);
        Set<String> keyWords = sensitiveWordsService.findSensitiveWords();
        if (keyWords.size() != 0){
            //初始化敏感词库
            SensitiveWordsUtil.init(keyWords);
            boolean flag = SensitiveWordsUtil.contains(content);
            System.out.println(flag);
            if (flag){
                String replaceContent = SensitiveWordsUtil.replaceSensitiveWord(content,'*');
                Set<String> sensitiveWords = SensitiveWordsUtil.getSensitiveWord(content);
                map.put("result",replaceContent);
                map.put("sensitiveWords",sensitiveWords);
            }else {
                map.put("result","allow");
            }
            return map;
        }
        return map;
    }
}
