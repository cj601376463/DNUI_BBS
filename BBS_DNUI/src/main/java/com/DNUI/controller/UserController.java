package com.DNUI.controller;

import com.DNUI.domain.User;
import com.DNUI.service.*;
import com.DNUI.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping("user")//浏览器访问路径的根目录
@SessionAttributes("user")//这里用了@SessionAttributes，可以直接把model中的user(也就key)放入其中,这样保证了session中存在user这个对象
public class UserController {

    private final IUserService userService;
    private final ITopicService topicService;
    private final IAttentionService attentionService;
    private final ICollectService collectService;
    private final IArticleService articleService;

    @Autowired
    public UserController(IUserService userService, ITopicService topicService, IAttentionService attentionService, ICollectService collectService, IArticleService articleService) {
        this.userService = userService;
        this.topicService = topicService;
        this.attentionService = attentionService;
        this.collectService = collectService;
        this.articleService = articleService;
    }

    //正常访问login页面
    @RequestMapping("/login")
    public String login(){
        return "user_login";
    }
    //正常访问showPage_user页面
    @RequestMapping("/showPage_user")
    public String showPage_user(){
        return "user_showPage";
    }
    //正常访问articleDetail页面
    @RequestMapping("/articleDetail_user")
    public String articleDetail_user(){
        return "user_articleDetail";
    }
    @RequestMapping("/upload_test")
    public String upload_test(){
        return "upload_test";
    }
    @RequestMapping("/chart")
    public String chart(){
        return "admin_firstPage";
    }
    //注销方法
    @RequestMapping(value = "outLogin")
    public String outLogin(HttpSession session,Model model){
        //通过session.invalidata()方法来注销当前的session
        session.invalidate();
        model.addAttribute("massage","注销成功，请重新登录！");
        return "forward:/user/login";
    }
    //转到用户信息页面，并且将基本信息显示出来
    @RequestMapping(value = "/{user_id}")
    public String mySpace(@PathVariable(value = "user_id")Integer user_id, Model model){
        model.addAttribute("user", userService.attentionAndCollectNumber(user_id));
        return "user_mySpace";
    }
    //转到自己的用户信息主页面
    @RequestMapping(value = "/myInfo")
    public String myInfo() {
        return "/user_myInfo";
    }
    //跳转到信息编辑页面
    @RequestMapping(value = "/editMyInfo")
    public String editMyInfo() {
        return "/user_editMyInfo";
    }
    //信息编辑页面功能
    @RequestMapping(value = "/myInfoUpdate")
    public String myInfoUpdate(@RequestParam("imgUrl") MultipartFile imgUrl, Integer user_id, HttpServletRequest request, HttpSession session,
                               String username, String sex, String phone, String sdept, String clazz, String sign, Model model)throws Exception{
        User user = (User) session.getAttribute("user");
        user.setUsername(username);
        user.setSex(sex);
        user.setPhone(phone);
        user.setSdept(sdept);
        user.setClazz(clazz);
        user.setSign(sign);
        user.setUser_id(user_id);
        if (imgUrl != null){
            if (!imgUrl.isEmpty()){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSS");
                String res = sdf.format(new Date());
                // uploads文件夹位置
                String Path =request.getServletContext().getRealPath("/images/heads/");
                //原始名称
                String originalFileName = imgUrl.getOriginalFilename();
                //新名称
                String newFileName = "sliver" + res + originalFileName.substring(originalFileName.lastIndexOf("."));
                // 创建年月文件夹
                Calendar date = Calendar.getInstance();
                File dateDirs = new File(date.get(Calendar.YEAR) + File.separator + (date.get(Calendar.MONTH)+1));
                // 新文件
                File newFile = new File(Path + File.separator + dateDirs + File.separator + newFileName);
                // 判断目标文件所在目录是否存在
                if( !newFile.getParentFile().exists()) {
                    // 如果目标文件所在的目录不存在，则创建父目录
                    newFile.getParentFile().mkdirs();
                }
                System.out.println(newFile);
                // 将内存中的数据写入磁盘
                imgUrl.transferTo(newFile);
                // 完整的url
                String fileUrl = date.get(Calendar.YEAR) + "/" + (date.get(Calendar.MONTH)+1) + "/" + newFileName;
                user.setImgUrl(fileUrl);
            }
        }
        userService.updateUserInfo(user);
        model.addAttribute("message","用户信息更新成功");
        request.getSession().setAttribute("user",userService.findUserById(user_id));
        return "forward:/user/myInfo";
    }
    //用户所有的帖子
    @RequestMapping(value = "/myTopics")
    public String myTopics(Model model, Integer user_id){
        model.addAttribute("topicList",topicService.findTopicByUserId(user_id));
        return "user_myArticles";
    }
    //用户自己进行帖子删除
    @RequestMapping(value = "/delMyArticles")
    public String delMyArticles(@RequestParam("topic_id") Integer topic_id,@RequestParam("user_id") Integer user_id){
        topicService.deleteTopic(topic_id,user_id);
        collectService.cancelCollect(user_id,topic_id);
        articleService.delArticle(topic_id);
        return "forward:/user/myTopics";
    }
    //根据用户id得到该用户所有关注的用户，并且展示
    @RequestMapping(value = "/myAttention")
    public String myAttention(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        Integer user_id = user.getUser_id();
        //每次取消关注后都会转到这里，所以需要更新一下session
        session.setAttribute("user", userService.findUserById(user_id));
//        System.out.println("controller："+attentionService.findAttentionByUserId(user_id));
        model.addAttribute("attentionList",attentionService.findAttentionByUserId(user_id));
        return "user_myAttention";
    }
    //取消关注（删除一条记录）
    @RequestMapping("/cancelAttention")
    public String cancelAttention(Model model, HttpSession session, @RequestParam("puser_id") Integer puser_id){
        User user = (User) session.getAttribute("user");
        Integer user_id = user.getUser_id();
        System.out.println(user_id);
        System.out.println(puser_id);
        attentionService.cancelAttention(user_id, puser_id);
        model.addAttribute("message", "取消关注成功");
        return "forward:myAttention";
    }
    //收藏页面
    @RequestMapping(value = "myCollect")
    public String MyCollect(Model model, Integer user_id){
        System.out.println("收藏页面的用户ID:"+user_id);
        model.addAttribute("collectList", collectService.findCollectsByUserId(user_id));
        return "user_myCollect";
    }
    //取消收藏（并且取消后回到原来的界面)
    @RequestMapping(value = "/cancelMyCollect")
    public String cancelMyCollect(Model model, Integer user_id, Integer topic_id, HttpSession session){
        collectService.cancelCollect(user_id,topic_id);
        //每次取消一条关注后都应该更新session
        session.setAttribute("user", userService.findUserById(user_id));
        model.addAttribute("message", "已取消该条收藏");
        return "forward:myCollect";
    }
    //转到用户更改密码的界面
    @RequestMapping(value = "/changePwd")
    public String changePwd() {
        return "/user_changePwd";
    }
    //更新用户的密码
    @RequestMapping(value = "/changePwdInfo")
    public String changePwdInfo(String password, Integer user_id, Model model){
        String passwordByMD5 = MD5Util.MD5(password);
        if (password == null || user_id == null){
            model.addAttribute("message","新密码不可为空");
        }else {
            userService.updatePwdByUser_id(passwordByMD5,user_id);
            model.addAttribute("message","更新密码成功");
        }
        return "forward:changePwd";
    }
    //ajax请求用户初始密码是否正确
    @RequestMapping("/checkInitPwd")
    public void checkInitPwd(HttpServletResponse response, String initPwd, Integer user_id) throws IOException {
        String passwordByMd5 = MD5Util.MD5(initPwd);
        User user = userService.checkInitPwd(passwordByMd5, user_id);
        if (user == null) {
            response.getWriter().write("false");
        }
    }
    //测试MD5登陆
    @RequestMapping(value = "login_check", method = RequestMethod.POST)
    public String login(@RequestParam("login_name") String login_name,@RequestParam("password") String password, Model model) {
        System.out.println("开始登陆");
        String passwordByMd5 = MD5Util.MD5(password);
        System.out.println("password:"+password+" 转换："+passwordByMd5);
        User user = userService.login(login_name, passwordByMd5);
        if (user != null) {
            System.out.println("登陆成功");
            model.addAttribute("user",user);
            return "redirect:/topic/all";
        } else {
            model.addAttribute("message","用户名或密码错误");
            return "user_login";
        }
    }
    //测试MD5注册


    @RequestMapping(value = "implAdd", method = RequestMethod.POST)
    @ResponseBody
    public int add(String login_name, String password,String email,String username) {
        System.out.println("开始注册");
        String passwordByMd5 = MD5Util.MD5(password);
        System.out.println("password:"+password+" 转换："+passwordByMd5);
        System.out.println(login_name+password+email+username);
        int findUserByLgName = userService.findByIfUserName(login_name);
        System.out.println(findUserByLgName);
        int findEmailByLgName = userService.findEmailByLgName(email);
        System.out.println(findEmailByLgName);
        int message;
        if (login_name.length() == 0 || password.length() == 0 || email.length() == 0 || username.length() == 0) {
            message = -1;
//            System.out.println("用户信息输入信息有误");
        } else if (findUserByLgName == 0 && findEmailByLgName == 0) {
            userService.register(login_name, passwordByMd5, username,email);
            message = 1;
//            System.out.println("可以注册");
        } else if (findUserByLgName !=0){
//            System.out.println("用户存在");
            message = 0;
        } else {
//            System.out.println("邮箱存在");
            message = 2;
        }
        System.out.println("message"+message);
        return message;
    }
    //用户设置别人禁止访问自己的空间,因为用户信息是从session中取出来的，所以要将session信息更新才行
    @RequestMapping("setIsLock")
    public ModelAndView setIsLock(ModelAndView mv, Integer user_id, Integer isLock, HttpSession session) {
        userService.updateIsLockById(user_id, isLock);
        //再回到自己的空间
        mv.setViewName("forward:" + user_id);
        session.setAttribute("user", userService.findUserById(user_id));
        if (isLock == 1) {
            mv.addObject("message", "禁止访问设置成功");
        } else {
            mv.addObject("message", "已设置为允许别人访问");
        }
        return mv;
    }
}
