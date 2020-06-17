package com.DNUI.controller;

import com.DNUI.domain.Article;
import com.DNUI.domain.ArticleVO;
import com.DNUI.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/article")
public class ArticleController {
    private final IArticleService articleService;

    public ArticleController(IArticleService articleService) {
        this.articleService = articleService;
    }

    //用户回复送到这里
    @RequestMapping(value = "/postArticle")
    public String postArticle(HttpServletRequest request , HttpSession session, Model model){
        if(session.getAttribute("user")==null){
            model.addAttribute("message","请先登录");
            return "user_login";
        }
        System.out.println("开始回复");
        String topic_id = request.getParameter("topic_id");
        String content = request.getParameter("content");
        String arefid = request.getParameter("arefid");
        String puser_id = request.getParameter("puser_id");
        String user_id = request.getParameter("user_id");
        ArticleVO article = new ArticleVO();
        article.setTopic_id(Integer.valueOf(topic_id));
        System.out.println("1:"+article.getTopic_id());
        System.out.println("content:"+content);
        article.setArefid(Integer.valueOf(arefid));
        System.out.println(arefid);
        System.out.println("2:"+article.getArefid());
        article.setPuser_id(Integer.valueOf(puser_id));
        System.out.println(puser_id);
        System.out.println("3:"+article.getPuser_id());
        article.setUser_id(Integer.valueOf(user_id));
        System.out.println(user_id);
        System.out.println("4:"+article.getUser_id());
        System.out.println("Controller article: "+article);
        articleService.replyArticle(article,content);
        return "redirect:/topic/detail/"+article.getTopic_id();
    }
}
