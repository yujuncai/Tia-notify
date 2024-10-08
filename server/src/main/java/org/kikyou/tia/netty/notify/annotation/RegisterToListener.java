package org.kikyou.tia.netty.notify.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


//用于对多个namespace注册listenner的扫描
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RegisterToListener {
    //暂时没用
    Boolean opened = true;

}


