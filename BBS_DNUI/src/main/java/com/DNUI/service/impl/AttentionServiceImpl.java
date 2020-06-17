package com.DNUI.service.impl;

import com.DNUI.dao.IAttentionDao;
import com.DNUI.dao.IUserDao;
import com.DNUI.domain.Attention;
import com.DNUI.service.IAttentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("attentionService")

public class AttentionServiceImpl implements IAttentionService {

    private final IAttentionDao attentionDao;
    private final IUserDao userDao;
    @Autowired
    public AttentionServiceImpl(IAttentionDao attentionDao, IUserDao userDao) {
        this.attentionDao = attentionDao;
        this.userDao = userDao;
    }

    @Override
    public List<Attention> findAttentionByUserId(Integer user_id) {
        List<Attention> attentions = attentionDao.findAttentionByUserId(user_id);
        for (Attention attention : attentions){
            attention.setUser(userDao.findUserById(user_id));
            attention.setPUser(userDao.findUserById(attention.getPuser_id()));
            System.out.println(attention);
        }
        return attentions;
    }

    @Override
    public Attention findOneAttention(Integer user_id, Integer puser_id) {
        return attentionDao.findOneAttention(user_id, puser_id);
    }

    @Override
    public void attentionUser(Integer user_id, Integer puser_id) {
        attentionDao.attentionUser(user_id, puser_id);
    }

    @Override
    public void cancelAttention(Integer user_id, Integer puser_id) {
        attentionDao.cancelAttention(user_id, puser_id);
    }
}
