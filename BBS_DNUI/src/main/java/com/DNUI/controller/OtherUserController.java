package com.DNUI.controller;

import com.DNUI.domain.Attention;
import com.DNUI.domain.User;
import com.DNUI.service.impl.AttentionServiceImpl;
import com.DNUI.service.impl.CollectServiceImpl;
import com.DNUI.service.impl.TopicServiceImpl;
import com.DNUI.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/otherSpace")
public class OtherUserController {
    private final UserServiceImpl userService;
    private final TopicServiceImpl topicService;
    private final AttentionServiceImpl attentionService;
    private final CollectServiceImpl collectService;
    @Autowired
    public OtherUserController(UserServiceImpl userService, TopicServiceImpl topicService, AttentionServiceImpl attentionService, CollectServiceImpl collectService) {
        this.userService = userService;
        this.topicService = topicService;
        this.attentionService = attentionService;
        this.collectService = collectService;
    }


    //转到别人的用户信息的主界面
    @RequestMapping(value = "/{user_id}")
    public String otherSpace(@PathVariable("user_id") Integer user_id, HttpSession session, Model model){
        User user = userService.findUserById(user_id);
        if (user.getIsLock() == 1){
//            model.addAttribute("message","用户空间已被禁止访问");
//            return "forward:/topic/all";
        }
        session.setAttribute("otherUser",user);
        return "user_otherSpace";
    }
    //显示用户信息
    @RequestMapping(value = "/otherInfo")
    public String showOtherInfo() {
        return "user_otherInfo";
    }
    //转到用户帖子页面
    @RequestMapping(value = "/otherArticles")
    public String otherArticles(Model model, Integer user_id) {
        model.addAttribute("otherTopicList", topicService.findTopicByUserId(user_id));
        return "user_otherArticles";
    }
    //转到用户关注页面
    @RequestMapping(value = "/otherAttention")
    public String otherAttention(HttpSession session, Model model){
        User user = (User) session.getAttribute("otherUser");
        Integer user_id = user.getUser_id();
        //每次取消关注后都会转到这里，所以需要更新一下session
        session.setAttribute("otherUser", userService.findUserById(user_id));
        model.addAttribute("otherAttentionList",attentionService.findAttentionByUserId(user_id));
        return "user_otherAttention";
    }
    //转到用户的所有的收藏
    @RequestMapping(value = "/otherCollect")
    public String otherCollect(Integer user_id, Model model){
        model.addAttribute("otherCollectList", collectService.findCollectsByUserId(user_id));
        return "user_otherCollect";
    }
    //关注一个用户
    @RequestMapping(value = "/attentionUser")
    public String attentionUser(HttpSession session, Integer puser_id, Model model){
        //从sesssion中取出用户信息
        User user = (User) session.getAttribute("user");
        //被关注的用户id
        Integer user_id = user.getUser_id();
        Attention attention = attentionService.findOneAttention(user_id, puser_id);
        if(attention!=null) {
            model.addAttribute("message", "关注失败，请不要重复关注!");
        } else {
            attentionService.attentionUser(user_id, puser_id);
            model.addAttribute("message", "关注用户成功");
            //关注成功后更新session中的user
            session.setAttribute("user", userService.findUserById(user_id));
        }
        return "forward:/otherSpace/" + puser_id;
    }
}
