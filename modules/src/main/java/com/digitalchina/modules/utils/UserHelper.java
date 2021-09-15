package com.digitalchina.modules.utils;

import com.digitalchina.modules.constant.UserConstant;
import com.digitalchina.modules.security.SecurityUtil;

/**
 * <p>
 *
 * </p>
 *
 * @author liuping
 * @since 2019-12-12
 */
public class UserHelper {

    /**
     * 获取用户id
     * 没有设置成-1，方便不登录测试
     * @return
     */
    public static Integer getUserId(){
        Integer userId = SecurityUtil.currentUserId();
        return null != userId ? userId : UserConstant.NO_USER;
    }
}
