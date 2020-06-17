package com.DNUI.utils;
import java.io.IOException;
import java.util.*;

public class SensitiveWordsUtil {
    /**
     * 最小匹配规则，如：敏感词库["小狗","小狗子"]，语句："我是小狗子"，匹配结果：我是[小狗]子
     */
    public static final int MinMatchType = 1;
    /**
     * 最大匹配规则，如：敏感词库["小狗","小狗子"]，语句："我是小狗子"，匹配结果：我是[小狗子]
     */
    public static final int MaxMatchType = 2;
    /**
     * 敏感词集合
     */
    public static HashMap sensitiveWordMap;

    /**
     * 初始化敏感词库， 构建DFA算法模型
     * @param sensitiveWordSet 敏感词库
     */
    public static synchronized void init(Set<String> sensitiveWordSet){
        initSensitiveWordMap(sensitiveWordSet);
    }

    /**
     * 初始化敏感词库，构建DFA算法模型
     * @param sensitiveWordSet 敏感词库
     */
    private static void initSensitiveWordMap(Set<String> sensitiveWordSet){
        //初始化敏感词词库，减少扩容操作
        sensitiveWordMap = new HashMap(sensitiveWordSet.size());
        String key;
        Map nowMap;
        Map<String, String> newWordMap;
        //迭代sensitiveWordSet
        Iterator<String> iterator = sensitiveWordSet.iterator();
        while(iterator.hasNext()){
            //关键字
            key = iterator.next();
            nowMap = sensitiveWordMap;
            for (int i = 0; i < key.length(); i++){
                //转换为char型
                char keyChar = key.charAt(i);
                //库中获取关键字
                Object wordMap = nowMap.get(keyChar);
                //如果存在该key，直接赋值，用于下一个循环获取
                if(wordMap != null){
                    nowMap = (Map) wordMap;
                }else {
                    //不存在则构建一个map，同时讲isEnd设置为0，因为并不是最后一个
                    newWordMap = new HashMap<>();
                    //不是最后一个
                    newWordMap.put("isEnd","0");
                    nowMap.put(keyChar,newWordMap);
                    nowMap = newWordMap;
                }
                if (i == key.length() - 1){
                    //最后一个
                    nowMap.put("isEnd","1");
                }
            }
        }
    }
    /**
     * 判断文字是否包含敏感字符
     * @param txt         文字
     * @param matchType   匹配规则：最大匹配、最小匹配
     * @return  若包含返回true，否则返回false
     */
    public static boolean contains(String txt, int matchType){
        boolean flag = false;
        for (int i = 0; i < txt.length(); i++){
            //判断是否包含敏感字符
            int matchFlag = checkSensitiveWord(txt,i,matchType);
            //大于0则存在，返回true
            if (matchFlag > 0){
                flag = true;
            }
        }
        return flag;
    }
    /**
     * 重载contains方法
     * @param txt 文字
     * @return 若包含返回true，否则返回false
     */
    public static boolean contains(String txt){
        return contains(txt,MaxMatchType);
    }
    /**
     * 获取文字中的敏感词
     * @param txt 文字
     * @param matchType 匹配规则
     * @return 返回文字中敏感词的列表
     */
    public static Set<String> getSensitiveWord(String txt, int matchType){
        Set<String> sensitiveWordList = new HashSet<>();
        for (int i = 0; i <txt.length(); i++){
            //判断是否包含敏感字符
            int length = checkSensitiveWord(txt,i,matchType);
            //存在则加到列表中
            if (length > 0){
                sensitiveWordList.add(txt.substring(i,i + length));
                //for会自增，所以i-1
                i = i + length - 1;
            }
        }
        return sensitiveWordList;
    }

    /**
     * 重载返回敏感词列表的方法
     * @param txt 文字
     * @return 返回列表中的敏感词
     */
    public static Set<String> getSensitiveWord(String txt){
        return getSensitiveWord(txt,MaxMatchType);
    }

    /**
     * 替换敏感字符
     * @param txt 文本
     * @param replaceChar 替换的字符，匹配的敏感词以字符逐个替换，如 语句：我爱小狗子 敏感词：小狗子，替换字符：*， 替换结果：我爱***
     * @param matchType 敏感词匹配规则
     * @return 替换内容
     */
    public static String replaceSensitiveWord(String txt, char replaceChar, int matchType){
        String resultTxt = txt;
        //获取所有敏感词
        Set<String> set = getSensitiveWord(txt,matchType);
        Iterator<String> iterator = set.iterator();
        String word;
        String replaceString;
        while(iterator.hasNext()){
            word = iterator.next();
            replaceString = getReplaceChars(replaceChar,word.length());
            resultTxt = resultTxt.replaceAll(word,replaceString);
        }
        return resultTxt;
    }
    /**
     * 重载
     * @param txt         文本
     * @param replaceChar 替换的字符，匹配的敏感词以字符逐个替换.
     * @return 重载
     */
    public static String replaceSensitiveWord(String txt, char replaceChar) {
        return replaceSensitiveWord(txt, replaceChar, MaxMatchType);
    }

    /**
     * 获取替换字符
     * @param replaceChar 替换字符
     * @param length 长度
     * @return 返回替换字符
     */
    private static String getReplaceChars(char replaceChar, int length){
        StringBuilder resultReplace = new StringBuilder(String.valueOf(replaceChar));
        for (int i = 1; i<length; i++){
            resultReplace.append(replaceChar);
        }
        return resultReplace.toString();
    }

    /**
     * 检查文字中是否包含敏感字符，检查规则如下：
     * @param txt 文字
     * @param beginIndex 开始点
     * @param matchType 类型
     * @return 如果存在，则返回敏感词字符的长度，不存在返回0
     */
    private static int checkSensitiveWord(String txt, int beginIndex, int matchType){
        //敏感词结束标识位：用于敏感词只有1位的情况
        boolean flag = false;
        //匹配标识数默认为0
        int matchFlag = 0;
        char word;
        Map nowMap = sensitiveWordMap;
        for (int i = beginIndex; i < txt.length(); i++){
            word = txt.charAt(i);
            //获取指定的key
            nowMap = (Map) nowMap.get(word);
            //存在，则判断是否为最后一个
            if (nowMap != null){
                //找到相应key，匹配标识+1
                matchFlag++;
                //如果为最后一个匹配规则,结束循环，返回匹配标识数
                if ("1".equals(nowMap.get("isEnd"))){
                    //结束标志位为true
                    flag = true;
                    //最小规则，直接返回,最大规则还需继续查找
                    if (MinMatchType == matchType) {
                        break;
                    }
                }
            }else {//不存在，则返回
                break;
            }
        }
        //长度必须大于等于1，为词
        if (matchFlag < 2 || !flag) {
            matchFlag = 0;
        }
        return matchFlag;
    }
    /**
     * 敏感词替换工具方法（对外方法）
     *
     * @param string 原本的字符
     * @return 输出的字符
     * @exception IOException 读写文件异常
     */
    public static String sensitiveHelper(String string, Set<String> sensitiveWords) throws IOException{
        //初始化词库
        SensitiveWordsUtil.init(sensitiveWords);
        //判断是否包含敏感词库
        if (contains(string)){
            //若包含返回替换后的字符
            String str = SensitiveWordsUtil.replaceSensitiveWord(string, '*');
            return str;
        }
        //不包含返回原本字符
        return string;
    }
}
