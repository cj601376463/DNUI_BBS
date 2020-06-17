package com.DNUI.controller;

import com.DNUI.domain.Admin;
import com.DNUI.service.IAdminService;
import com.DNUI.service.ITopicService;
import com.DNUI.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final IAdminService adminService;
    private final IUserService userService;
    private final ITopicService topicService;
    @Autowired
    public AdminController(IAdminService adminService, IUserService userService, ITopicService topicService){
        this.adminService = adminService;
        this.userService = userService;
        this.topicService = topicService;
    }
    //跳转图表首页
    @RequestMapping("firstPage")
    public String firstPage(){return "admin_firstPage";}
    //跳转到登录界面
    @RequestMapping("/login")
    public String login() {
        return "admin_login";
    }
    //接收信息，是否允许登录
    @RequestMapping(value = "index")
    public String checkAdminInfo(String account, String password, HttpSession session, Model model){
        System.out.println("进入了验证信息 " + account + "  , " + password);
        Admin admin = adminService.findAdmin(account,password);
        //首先满足基本条件
        if (account != null && !account.isEmpty() && password != null && !password.isEmpty()){
            //不为空说明帐号密码正确
            if (admin != null){
                session.setAttribute("admin",admin);
                return "admin_index";
            }else {
                model.addAttribute("message","用户名或密码错误，请重新登陆");
                return "forward:login";
            }
        }else {
            model.addAttribute("message","用户名或密码不能为空");
            return "forward:login";
        }
    }
    //管理员退出登录
    @RequestMapping("/cancel")
    public String cancelAdmin(HttpSession session) {
        //消除所有信息
        session.invalidate();
        return "admin_login";
    }
    //用户信息进行展示
    @RequestMapping("/userHandlerInfo")
    public String userHandlerInfo(Model model) {
        //获得所有用户
        model.addAttribute("userList", userService.findAllUser());
        return "admin_userHandle";
    }
    //用户状态进行更新
    @RequestMapping("/usersStatusUpdate")
    public String usersStatusUpdate(@RequestParam("id[]") Integer[] user_ids, @RequestParam("status") Integer status, Model model){
        if (user_ids == null || status == null) {
            model.addAttribute("message", "操作失败");
            return "forward:userHandlerInfo";
        }else {
            userService.updateUsersStatus(status,user_ids);
            model.addAttribute("message", "更新成功");
            //为0说明是从管理界面来的请求，再回到管理界面
            if(status == 0){
                return "forward:userHandlerInfo";
            } else {
                //说明是从用户禁言界面来的请求
                return "forward:shutUppedUser";
            }
        }
    }
    //跳转到被禁言的用户的界面，并且将信息显示出来
    @RequestMapping("/shutUppedUser")
    public String shutUppedUser(Model model){
        model.addAttribute("shutUserList", userService.findUsersByShut());
        return "admin_shutUppedUser";
    }
    //跳转到修改密码界面
    @RequestMapping("/changePwd")
    public String changePwd() {
        return "/admin_changPwd";
    }
    //管理员密码修改信息接收
    @RequestMapping("/changePwdInfo")
    public String changePwdInfo(Integer admin_id, String newPwd, String initPwd, Model model){
        System.out.println("这是修改密码的信息： " + newPwd + " , " + initPwd);
        if (admin_id == null || newPwd == null || initPwd == null) {
            model.addAttribute("message", "密码不能为空");
        } else {
            model.addAttribute("message", "密码修改成功");
            adminService.updatePwd(admin_id, newPwd, initPwd);
        }
        return "forward:changePwd";
    }
    //AJax检验密码是否正确
    @RequestMapping("checkInitPwd")
    public void checkInitPwd(HttpServletResponse response, String initPwd, Integer admin_id) {
        Admin admin = adminService.findAdminByInitPwd(admin_id, initPwd);
        if (admin == null) {
            try {
                response.getWriter().write("false");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //跳转到管理帖子的界面，并且显示信息
    @RequestMapping("/allTopic")
    public String allTopic(Model model,@RequestParam(value = "typeName", required = false) String typeName, @RequestParam(value = "topicName", required = false) String topicName){
        model.addAttribute("adminAllTopic", adminService.adminFindAllTopic(typeName,topicName));
        model.addAttribute("types", topicService.findAllType());
        return "admin_allTopic";
    }
    //转到已经被删除掉的帖子，并且显示信息
    @RequestMapping("/deletedTopic")
    public String deletedTopic(Model model, String type, String orderBy) {
        if (type == null || orderBy == null) {
            model.addAttribute("topicList", topicService.findDeletedTopic(type, orderBy));
        }
        return "admin_deletedTopic";
    }
    //模糊查找用户
    @RequestMapping("/findUsersByLikeName")
    public String findUsersByLikeName(@RequestParam("keywords") String username, Model model){
        System.out.println(username);
        model.addAttribute("username", username);
        if (username == null || username.isEmpty()){
            model.addAttribute("message", "用户名不能为空");
            //为了防止报错，做了一条假数据，一旦为空，直接转发到用户主界面
            return "forward:userHandlerInfo";
        }else {
            model.addAttribute("userList", userService.findUsersByLikeName(username));
        }
        model.addAttribute("username", username);
        return "/admin_userHandle";
    }
    //更新帖子的状态，置顶
    @RequestMapping("/updateTopicStatus")
    public String updateTopicStatus(Model model, Integer st, String type, @RequestParam("id[]") Integer[] ids, @RequestParam(required = false) String initUrl){
        System.out.println("更新帖子的参数： " + st + " ," + type + " , " + ids[0]);
        topicService.updateTopicsStatus(type, st, ids);
        model.addAttribute("message", "更新成功");
        if (initUrl!=null&&initUrl.equals("deletedTopic")) {
            //回到恢复帖子的界面
            return "forward:deletedTopic";
        }
        return "forward:allTopic";
    }
    //删除帖子
    @RequestMapping("/realDelete")
    public String realDeleteTopic(@RequestParam("soloId") Integer soloId,@RequestParam("soloIdUserId") Integer soloIdUserId, Model model){
        topicService.deleteTopic(soloId,soloIdUserId);
        return "forward:deletedTopic";
    }
    //模糊查找被禁言的用户
    @RequestMapping("/findShutUsersByLikeName")
    public String findShutUsersByLikeName(@RequestParam("keywords") String username, Model model){
        System.out.println(username);
        model.addAttribute("username", username);
        if (username == null || username.isEmpty()){
            model.addAttribute("message", "用户名不能为空");
            //为了防止报错，做了一条假数据，一旦为空，直接转发到用户主界面
            return "forward:shutUppedUser";
        }else {
            model.addAttribute("shutUserList", userService.findShutUsersByLikeName(username));
            System.out.println(userService.findShutUsersByLikeName(username));
        }
        model.addAttribute("username", username);
        return "/admin_shutUppedUser";
    }
    //模糊查找被禁言的用户
    @RequestMapping("/findDeleteTopicByLikeName")
    public String findDeleteTopicByLikeName(@RequestParam("keywords") String TopicName, Model model){
        System.out.println(TopicName);
        if (TopicName == null || TopicName.isEmpty()){
            model.addAttribute("message", "用户名不能为空");
            //为了防止报错，做了一条假数据，一旦为空，直接转发到用户主界面
            return "forward:deletedTopic";
        }else {
            model.addAttribute("deletedTopicList", topicService.findDeleteTopicByLikeName(TopicName));
            System.out.println(topicService.findDeleteTopicByLikeName(TopicName));
        }
        return "/admin_deletedTopic";
    }
}
