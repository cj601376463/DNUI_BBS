package com.DNUI.controller;

import com.DNUI.dao.ICollectDao;
import com.DNUI.domain.*;
import com.DNUI.service.ITopicService;
import com.DNUI.service.IUserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/topic")
@SessionAttributes("topic")//这里用了@SessionAttributes，可以直接把model中的topic(也就key)放入其中,这样保证了session中存在topic这个对象
public class TopicController {

    private final ITopicService topicService;
    private final IUserService userService;
    private final ICollectDao collectDao;
    @Autowired
    public TopicController(ITopicService topicService, IUserService userService, ICollectDao collectDao) {
        this.topicService = topicService;
        this.userService = userService;
        this.collectDao = collectDao;
    }

    @RequestMapping("/all")
    public String findAllTopic(Model model,@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "30")int size, @RequestParam(value = "type", required = false) String type, @RequestParam(value = "topicName", required = false) String topicName) {
        List<TopicVO> topicVO = topicService.findAllTopic(page,size,type,topicName);
        //分页
        PageInfo pageInfo = new PageInfo<>(topicVO);
        if (topicVO != null){
            model.addAttribute("pageInfo",pageInfo);//分页
            return "user_showPage";
        }
        return "user_loginFail";
    }
    @RequestMapping(value = "/detail/{topic_id}")
    public String showTopicDetailById(Model model,@PathVariable("topic_id") int topic_id){
        TopicVO topicVO = topicService.findTopicById(topic_id);
        List<ArticleVO> articleVOS = topicService.findArticlesByTopicId(topic_id);
        topicVO.setOneToArticles(articleVOS);
        if (topicVO != null){
            model.addAttribute("topicVO",topicVO);
//            System.out.println("detailController成功："+ topicVO);
            return "user_articleDetail";
        }
        return "user_loginFail";
    }

    //验证权限，然后转到发帖页面
    @RequestMapping(value = "/postTopic")
    public String postTopic(HttpSession session, Model model){
        System.out.println("进入了发帖页面");
        if(session.getAttribute("user")==null){
            model.addAttribute("message","请先登录");
            return "user_login";
        }else {
            List<Type> types = topicService.findAllType();
            model.addAttribute("type",types);
            return "user_postArticle";
        }
    }
    //接收发帖子的信息，并且进行更新
    @RequestMapping(value = "/postTopicInfo")
    public String postTopicInfo(HttpSession session, HttpServletRequest request){
        User user = (User) session.getAttribute("user");
        System.out.println(user.getUser_id()+","+user.getUsername());
        String topic = request.getParameter("topic");
        String content = request.getParameter("content");
        String typeName = request.getParameter("typeName");
        System.out.println("topicColler:"+topic + content + typeName);
        //发帖
        userService.postTopic(user.getUser_id(), topic, content,typeName);
        return "redirect:/topic/all";
    }
    //收藏帖子
    @RequestMapping(value = "/collectTopic")
    public String collectTopic(HttpSession session, Integer topic_id, Model model){
        User user =(User) session.getAttribute("user");
        Collect collect = collectDao.findOneCollect(user.getUser_id(), topic_id);
        if(collect != null){
            model.addAttribute("message","收藏失败，该帖子已经在收藏列表中！");
        }else {
            collectDao.collectTopic(user.getUser_id(),topic_id);
            model.addAttribute("message", "收藏成功！");
        }
        session.setAttribute("user", userService.findUserById(user.getUser_id()));
        return "forward:/topic/detail/" + topic_id;
    }
}
