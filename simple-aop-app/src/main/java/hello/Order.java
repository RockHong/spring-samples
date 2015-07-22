package hello;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Order {
    private static final Logger LOGGER = LoggerFactory.getLogger(Order.class);
    
    public String getCustomer() {
        return "hong";
    }
    
    @Auditable
    public double calculateAmount(double amount, double discount) {
    	try {
			Thread.sleep(getSleepTime());
		} catch (InterruptedException e) {
			LOGGER.info("sleep is interrupted");
		}
        return amount*discount;
    }
    
    private long getSleepTime() {
    	//http://stackoverflow.com/questions/5887709/getting-random-numbers-in-java
    	int MAX = 5000;
    	
    	long ret1 = 0;
    	Random random = new Random(System.currentTimeMillis());
    	ret1 = random.nextInt(MAX);
    	
    	long ret2 = 0;
    	ret2 = (long)(Math.random() * MAX + 1);
    	
    	return ret1;
    }
}
