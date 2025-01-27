package com.workcheng.weiya.common.schedule;
import com.workcheng.weiya.common.websocket.WsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;



/**
 * 测试消息生成 job（可通过 @EnableScheduling 来生效）
 * @author andy
 * @Date: 2021/5/25 17:54
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageJob {
    private final WsService wsService;
    private static ObjectMapper mapper;

    /**
     * 每5s发送
     */
    @Scheduled(cron = "0/5 * * * * *")
    public void run() {
        try {
            HashMap<String, Object> msg = new HashMap<>();
            msg.put("content", "自动生成消息 " + LocalDateTime.now().toString());
            msg.put("headImgUrl", "http://static.clewm.net/cli/images/beautify/logo/icon1.png");
            wsService.broadcastMsg(getMapperInstance(false).writeValueAsString(msg));
        } catch (IOException e) {
            log.error("error", e);
        }
    }

    /**
     * 获取ObjectMapper实例
     *
     * @param createNew 方式：true，新实例；false,存在的mapper实例
     * @return
     */
    public static synchronized ObjectMapper getMapperInstance(boolean createNew) {
        if (createNew) {
            return new ObjectMapper();
        } else if (mapper == null) {
            mapper = new ObjectMapper();
        }
        return mapper;
    }
}
