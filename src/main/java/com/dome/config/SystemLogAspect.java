package com.dome.config;

import com.dome.unti.SystemLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @program: dome2
 * @description: 视图层日志aop
 * @author: Mr.Zhou
 * @create: 2018-12-14 11:20
 **/

@Aspect
@Component
public class SystemLogAspect {

    @Pointcut("@annotation(com.dome.unti.SystemLog)")
    public  void controllerAspect() {}

    @Pointcut("@annotation(com.dome.unti.SystemLog)")
    public  void serviceAspect() {}

    @Pointcut("@annotation(com.dome.unti.SystemLog)")
    public  void repositoryAspect() {}

    @After("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String ip = request.getRemoteAddr();
            String description = getControllerMethodDescription(joinPoint);
            Object obj = request.getSession().getAttribute("loginUser");
//            LogUser user = new LogUser(null, null);
//            /*对象obj中必须拥有属性account、userName*/
//            BeanUtils.copyProperties(user, obj);
//            if(StringUtils.isBlank(user.getAccount())){
//                user = new LogUser("Anonymous", "匿名用户");
//            }
        } catch (Exception e) {

        }
    }

    @SuppressWarnings("rawtypes")
    private static String getControllerMethodDescription(JoinPoint joinPoint)  throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(SystemLog.class).description();
                    break;
                }
            }
        }
        return description;
    }
}
