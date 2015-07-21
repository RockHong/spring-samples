package hello;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class SimpleAspect {
    private static final Logger logger = LoggerFactory.getLogger(SimpleAspect.class);
    
    @Before("hello.SimpleAspect.logPublicMethods()")
    public void logEntering(JoinPoint joinPoint) {
        logger.info("*** ENTERING " + joinPoint.toString());
    }
    
    @After("hello.SimpleAspect.logPublicMethods()")
    public void logExiting(JoinPoint joinPoint) {
        logger.info("*** EXITING " + joinPoint.toString());
    }
    
    @Pointcut("execution(public * *(..))")
    public void logPublicMethods() { }
}
