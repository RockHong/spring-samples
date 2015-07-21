package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Order {
    private static final Logger logger = LoggerFactory.getLogger(Order.class);
    
    public String getCustomer() {
	return "hong";
    }
    
    public double calculateAmount(double amount, double discount) {
	return amount*discount;
    }
}
