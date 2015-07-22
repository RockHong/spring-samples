package hello;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

@Aspect
public class AnnotationAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationAspect.class);
    
    @Before("hello.AnnotationAspect.logPublicMethods()")
    public void logEntering(JoinPoint joinPoint) {
        LOGGER.info("*** ENTERING " + joinPoint.toString());
    }
    
    @After("hello.AnnotationAspect.logPublicMethods()")
    public void logExiting(JoinPoint joinPoint) {
        LOGGER.info("*** EXITING " + joinPoint.toString());
    }
    
    @Pointcut("execution(public * *(..))")
    public void logPublicMethods() { }
    
    @Around("@annotation(Auditable)")
    public Object recordExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    	LOGGER.info("Profiling " + joinPoint.toString() + ", args " + joinPoint.getArgs().toString());
    	// spring provides a stop watch implementation
    	StopWatch clock = new StopWatch("Execution Time Clock");
    	
    	try {
    		clock.start(joinPoint.toString());
    		return joinPoint.proceed();
    	} finally {
    		clock.stop();
    		LOGGER.info("*** " + clock.prettyPrint() + " " + clock.getLastTaskTimeMillis());
    	}
    }
}
