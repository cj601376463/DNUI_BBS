package com.DNUI.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ISensitiveWordsDao {
    //查找所有敏感词
    @Select("Select * from sensword")
    Set<String> findSensitiveWords();
}
