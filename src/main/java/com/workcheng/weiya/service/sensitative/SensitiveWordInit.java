package com.workcheng.weiya.service.sensitative;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.*;


/**
 * @author zhanghao
 * @date 2017/1/5
 */
@Service
@Slf4j
public class SensitiveWordInit {
    //字符编码
    private static final String ENCODING = "UTF-8";
    private static final String WORD_LOCATION = "sensitive/sensitiveWord.txt";
    @SuppressWarnings("rawtypes")
    public HashMap sensitiveWordMap;

    public SensitiveWordInit() {
        super();
    }

    /**
     * @Description: 初始化敏感词库，将敏感词加入到HashMap中，构建DFA算法模型
     */
    @SuppressWarnings("rawtypes")
    public Map initKeyWord() {
        try {
            //读取敏感词库
            Set<String> keyWordSet = readSensitiveWordFile();
            //将敏感词库加入到HashMap中
            addSensitiveWordToHashMap(keyWordSet);
            //spring获取application，然后application.setAttribute("sensitiveWordMap",sensitiveWordMap);
        } catch (Exception e) {
            log.error("error", e);
        }
        return sensitiveWordMap;
    }

    /**
     * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：<br>
     * 中 = {
     * isEnd = 0
     * 国 = {<br>
     * isEnd = 1
     * 人 = {isEnd = 0
     * 民 = {isEnd = 1}
     * }
     * 男  = {
     * isEnd = 0
     * 人 = {
     * isEnd = 1
     * }
     * }
     * }
     * }
     * 五 = {
     * isEnd = 0
     * 星 = {
     * isEnd = 0
     * 红 = {
     * isEnd = 0
     * 旗 = {
     * isEnd = 1
     * }
     * }
     * }
     * }
     *
     * @param keyWordSet 敏感词库
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private void addSensitiveWordToHashMap(Set<String> keyWordSet) {
        sensitiveWordMap = new HashMap(keyWordSet.size());
        //初始化敏感词容器，减少扩容操作
        String key = null;
        Map nowMap = null;
        Map<String, String> newWorMap = null;
        //迭代keyWordSet
        for (String s : keyWordSet) {
            key = s;
            //关键字
            nowMap = sensitiveWordMap;
            for (int i = 0; i < key.length(); i++) {
                char keyChar = key.charAt(i);
                //转换成char型
                Object wordMap = nowMap.get(keyChar);
                //获取
                if (wordMap != null) {
                    //如果存在该key，直接赋值
                    nowMap = (Map) wordMap;
                } else {
                    //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<String, String>();
                    newWorMap.put("isEnd", "0");
                    //不是最后一个
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if (i == key.length() - 1) {
                    nowMap.put("isEnd", "1");
                    //最后一个
                }
            }
        }
    }

    private InputStream load() {
        InputStream inputStream = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (WORD_LOCATION.startsWith("classpath:")) {
            inputStream = loader.getResourceAsStream(WORD_LOCATION.substring(10));
        } else {
            inputStream = loader.getResourceAsStream(WORD_LOCATION);
            if (inputStream == null) {
                inputStream = SensitiveWordInit.class.getClassLoader().getResourceAsStream(WORD_LOCATION);
            }
        }
        return inputStream;
    }

    /**
     * 读取敏感词库中的内容，将内容添加到set集合中
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("resource")
    private Set<String> readSensitiveWordFile() throws Exception {
        Set<String> set = null;
        //读取文件
        try (InputStreamReader read = new InputStreamReader(load(), ENCODING)) {
            if (read.ready()) {
                //文件流是否存在
                set = new HashSet<String>();
                BufferedReader bufferedReader = new BufferedReader(read);
                String txt = null;
                while ((txt = bufferedReader.readLine()) != null) {
                    //读取文件，将文件内容放入到set中
                    set.add(txt);
                }
            } else {
                //不存在抛出异常信息
                throw new Exception("敏感词库文件不存在");
            }
        } catch (Exception e) {
            throw e;
        }
        //关闭文件流
        return set;
    }
}
