package org.kikyou.tia.netty.chatroom.utils;

import cn.hutool.core.util.StrUtil;
import lombok.experimental.UtilityClass;

/**
 *
 * @author xiaowu
 */
@UtilityClass
public class IpUtils {

    public String getDeviceType(String userAgent) {
        boolean bIsIpad = StrUtil.contains(userAgent, "ipad");
        boolean bIsIphoneOs = StrUtil.contains(userAgent, "iphone os");
        boolean bIsMidp = StrUtil.contains(userAgent, "midp");
        boolean bIsUc7 = StrUtil.contains(userAgent, "rv:1.2.3.4");
        boolean bIsUc = StrUtil.contains(userAgent, "ucweb");
        boolean bIsAndroid = StrUtil.contains(userAgent, "android");
        boolean bIsCE = StrUtil.contains(userAgent, "windows ce");
        boolean bIsWM = StrUtil.contains(userAgent, "windows mobile");
        if (bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM) {
            return "phone";
        } else {
            return "pc";
        }
    }
}
