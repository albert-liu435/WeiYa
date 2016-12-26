package com.zoe.weiya.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by andy on 2016/12/20.
 */
public class User extends OnlyUser{

    @NotBlank(message = "openId不能为空")
    private String openId;
    @NotBlank(message = "姓名不能为空")
    private String name;
    @NotNull(message = "是否订餐不能为空")
    private Integer order;

    private Integer priceCount;
    private String signFlag;//该时间段内的签到标识符
    public User() {
        super();
    }

    public User(String openId, String signFlag) {
        this.openId = openId;
        this.signFlag = signFlag;
    }

    public Integer getPriceCount() {
        return priceCount;
    }

    public void setPriceCount(Integer priceCount) {
        this.priceCount = priceCount;
    }

    public String getSignFlag() {
        return signFlag;
    }

    public void setSignFlag(String signFlag) {
        this.signFlag = signFlag;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "openid='" + openId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}