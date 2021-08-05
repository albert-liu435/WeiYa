package com.workcheng.weiya.controller;

import com.workcheng.weiya.common.constant.ErrorCode;
import com.workcheng.weiya.common.domain.User;
import com.workcheng.weiya.common.dto.MealOrder;
import com.workcheng.weiya.common.exception.NotStartException;
import com.workcheng.weiya.common.exception.ServerInternalException;
import com.workcheng.weiya.common.utils.MyDateUtil;
import com.workcheng.weiya.common.utils.ResponseUtil;
import com.workcheng.weiya.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author chenghui
 * @date 2017/1/3
 */
@RequestMapping("/meal")
@RestController
@RequiredArgsConstructor
public class MealController {
    private final UserService userService;

    /**
     * 获取订餐信息
     *
     * @return
     */
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public Object getOrder() {
        try {
            MealOrder mealOrder = new MealOrder();
            List<User> userList = userService.orderMealUserList();
            mealOrder.setOrderUsers(userList);
            mealOrder.setOrderCount(userList.size());
            mealOrder.setNow(MyDateUtil.moment());
            return ResponseUtil.success(mealOrder);
        } catch (NotStartException e) {
            return ResponseUtil.failure(ErrorCode.NOT_START);
        } catch (ServerInternalException e) {
            return ResponseUtil.failure(e.getMessage());
        }
    }
}