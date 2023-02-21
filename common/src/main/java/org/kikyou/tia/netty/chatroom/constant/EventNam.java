package org.kikyou.tia.netty.chatroom.constant;

/**
 * 事件名称
 * @author yujuncai
 */
public interface EventNam {

    String HISTORY_MESSAGE = "history-message";

    String HISTORY = "history";

    String REGISTER_FAIL = "registerFail";

    String REGISTER_SUCCESS = "registerSuccess";

    String LOGIN_FAIL = "loginFail";

    String SERVER_ERR = "serverErr";

    String LOGIN_SUCCESS = "loginSuccess";

    String MESSAGE = "message";

    String SYSTEM = "system";

    String DISCONNECT = "disconnect";

    String CONNECT = "connect";

    String LOGIN = "login";

    String LOGOUT = "logout";

    String REGISTER = "register";

    String ACK = "ack";
}
