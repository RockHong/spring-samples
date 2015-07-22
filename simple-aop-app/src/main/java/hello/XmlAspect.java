package hello;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlAspect {
	private static final Logger LOGGER = LoggerFactory.getLogger(XmlAspect.class);
	
    public void postInvoke(JoinPoint joinPoint) {
    	LOGGER.info("Finished invocation on " + joinPoint.toString());
    }
}
