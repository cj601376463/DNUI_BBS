package com.DNUI.service;

import java.util.List;
import java.util.Set;

public interface ISensitiveWordsService {
    //初始化敏感词库
    public Set<String> findSensitiveWords();
}
