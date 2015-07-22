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
    	Random random = new Random(System.currentTimeMillis());
    	try {
			Thread.sleep(random.nextLong() % 5000);
		} catch (InterruptedException e) {
			LOGGER.info("sleep is interrupted");
		}
        return amount*discount;
    }
}
