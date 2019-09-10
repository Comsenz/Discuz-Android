package cn.tencent.DiscuzMob.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by AiWei on 2016/4/20.
 */
@Documented
@Retention(value = RetentionPolicy.RUNTIME)//防止代码混淆时反射不到被注解的方法
@Target(value = ElementType.METHOD)
public @interface JSONParseMethod {
}
