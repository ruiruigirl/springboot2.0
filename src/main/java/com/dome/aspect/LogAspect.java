package com.dome.aspect;

import ch.qos.logback.classic.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @program: dome2
 * @description: 日志aop 检测所有Controller 输入数据定制日志 并日志到具体方法
 * @author: Mr.Zhou
 * @create: 2018-12-21 10:21
 **/

@Aspect
@Component
public class LogAspect {
    public static Logger logger = (Logger) LoggerFactory.getLogger(LogAspect.class);

    @Before( "execution(* com.dome.controller.*Controller.*(..))")
    public void LogBefore(JoinPoint joinPoint){
        StringBuilder sb = new StringBuilder();
        for (Object arg :
                joinPoint.getArgs()) {
            sb.append("arg:" + arg.toString());
        }
        sb.append(" StaticPart: "+ joinPoint.getStaticPart());
        logger.info("before method: " + sb.toString());
    }
}
