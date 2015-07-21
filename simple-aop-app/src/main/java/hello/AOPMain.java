package hello;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AOPMain {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/beans.xml");
        Order order = context.getBean("order", Order.class);
        
        order.getCustomer();
        order.calculateAmount(9.9, 0.9);
        
        // remember to release all beans
        context.close();
    }
}
