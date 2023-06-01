package org.kikyou.tia.netty.notify.web.vo;

import lombok.Data;
import org.kikyou.tia.netty.notify.web.utils.HttpServletUtil;


@Data
public class URL {

    private String url;

    public URL() {

    }

    /**
     * 封装URL地址，自动添加应用上下文路径
     *
     * @param url URL地址
     */
    public URL(String url) {
        this.url = HttpServletUtil.getRequest().getContextPath() + url;
    }

    @Override
    public String toString() {
        return this.url;
    }
}
