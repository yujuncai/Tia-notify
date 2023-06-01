package org.kikyou.tia.netty.notify.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author yujuncai
 */
@Getter
@AllArgsConstructor
public enum SystemType {
    JOIN("join"),LOGOUT("logout"), MONITOR_INFO("monitor_info"),MONITOR_UPTIME("monitor_uptime"),MONITOR_USAGE("monitor_usage");

    private final String name;
}
