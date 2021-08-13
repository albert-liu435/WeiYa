package com.workcheng.weiya.common.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author andy
 * @date 2017/1/16
 */
@Data
@Entity
public class LuckyUserMessage implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String openId;
    private String name;
    private Integer degree;
    private Timestamp signDate;
    private String depName;
    private Timestamp hitTime = new Timestamp(System.currentTimeMillis());
    private String message;
    private String nickName;

    @Override
    public String toString() {
        return "LuckyUserMessage{" +
                "openId='" + openId + '\'' +
                ", name='" + name + '\'' +
                ", degree=" + degree +
                ", signDate=" + signDate +
                ", depName='" + depName + '\'' +
                ", hitTime=" + hitTime +
                ", message='" + message + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
