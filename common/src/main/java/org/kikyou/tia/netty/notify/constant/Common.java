package org.kikyou.tia.netty.notify.constant;

/**
 * Common
 * @author yujuncai
 */
public interface Common {
    String TOKEN = "token";

    /**
     * socket client set user key
     */
    String USER_KEY = "user";

    /**
     * redis store save message data key(group_001 message)
     */
    String GROUP_001_MESSAGE = "group_001_message";

    /**
     * todo 扩展
     * 聊天室 id
     * 001
     */
    String GROUP_001_CHANNEL = "group_001";

    String SALT="TIA";


    String ID_KEY="id_key_";

    String DEFAULT="default";
}
