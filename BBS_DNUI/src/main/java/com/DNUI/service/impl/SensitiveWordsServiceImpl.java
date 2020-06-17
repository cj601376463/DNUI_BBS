package com.DNUI.service.impl;

import com.DNUI.dao.ISensitiveWordsDao;
import com.DNUI.service.ISensitiveWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service("sensitiveWordsService")
public class SensitiveWordsServiceImpl implements ISensitiveWordsService {
    private final ISensitiveWordsDao sensitiveWordsDao;
    @Autowired
    public SensitiveWordsServiceImpl(ISensitiveWordsDao sensitiveWordsDao) {
        this.sensitiveWordsDao = sensitiveWordsDao;
    }
    @Override
    public Set<String> findSensitiveWords() {
        return sensitiveWordsDao.findSensitiveWords();
    }
}
