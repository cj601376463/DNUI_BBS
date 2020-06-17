package com.DNUI.service;

import com.DNUI.domain.Admin;
import com.DNUI.domain.TopicVO;

import java.util.List;

public interface IAdminService {
    //通过帐号找到一个用户
    public Admin findAdmin(String account, String password);
    //更新管理员密码
    public void updatePwd(Integer admin_id, String newPwd, String initPwd);
    //验证密码
    public Admin findAdminByInitPwd(Integer admin_id, String initPwd);
    //查早所有用户
    public List<TopicVO> adminFindAllTopic(String type, String topicName);
    //删除帖子
    public void realDeleteTopic(Integer[] ids);
}
