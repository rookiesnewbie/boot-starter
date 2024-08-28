package com.lt.aspect;



import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

//日志切面
@Aspect
@Slf4j
@Component
public class ControllerLogAspect {



    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    //@Pointcut("execution(* com.lt.*.*.*(..))")
    @Pointcut("execution(* com.lt.*..*.*(..))")
    public void controllers() {
    }

    @Before("controllers()")
    public void deBefore(final JoinPoint joinPoint) throws UnknownHostException {
        // 接收到请求，记录请求内容
        log.debug("===========================================================");
        log.debug("================  Controller Log Start  ===================");
        log.debug("===========================================================");
        this.startTime = LocalDateTime.now();
        final ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            InetAddress localhost = InetAddress.getLocalHost();
            String localIpAddress = localhost.getHostAddress();
            final HttpServletRequest request = attributes.getRequest();
            log.debug("==> Request: [{}]{}", request.getMethod(), request.getRequestURL());
            log.debug("==> From IP: {}", localIpAddress);
        }
        log.debug(
                "==>  Method: {}",
                joinPoint.getSignature().getDeclaringTypeName() + "#" + joinPoint.getSignature().getName());
        log.debug("==>    Args: {}", Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 后置结果返回
     *
     * @param result 结果
     */
    @AfterReturning(pointcut = "controllers()", returning = "result")
    public void doAfterReturning(final Object result) {
        // 处理请求的时间差
        final long difference = ChronoUnit.MILLIS.between(this.startTime, LocalDateTime.now());
        log.debug("==>   Spend: {}s", difference / 1000.0);
        log.debug("==>  Return: {}", result);
        log.debug("================  Controller Log End  =====================");
    }

    /**
     * 后置异常通知
     */
    @AfterThrowing(pointcut = "controllers()", throwing = "e")
    public void doAfterThrowing(final Throwable e) {
        log.debug("==> Exception: {}", e.toString());
        e.printStackTrace();
        log.debug("================  Controller Log End  =====================");
    }

}
