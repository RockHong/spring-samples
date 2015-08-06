package hello;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainClass {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainClass.class);
    private static final String OPT_CREATE_DB = "-create-db";
    
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals(OPT_CREATE_DB)) {
            createDB();
        } else {
            run();
        }
    }
    
    private static void createDB() {
        
    }
    
    private static void run() {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
        try {
            emf = Persistence.createEntityManagerFactory("simplePU");
            em = emf.createEntityManager();
            prepareData(em);
            createOrder(em);    
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }
    
    private static void prepareData(EntityManager em) {
        BasicRepository<Customer> customerRepo = new BasicRepository<Customer>(em);
        BasicRepository<Product> productRepo = new BasicRepository<Product>(em);
        
        if (customerRepo.count() == 0) {
            EntityTransaction txn = em.getTransaction();
            try {
                txn.begin();
                for (int i = 0; i < 3; i++) {
                    Customer c = new Customer();
                    c.setName("customer_" + i);
                    customerRepo.save(c);
                }
                txn.commit();
                LOGGER.info("prepare customer data done.");
            } catch (Exception e) {
                if (txn.isActive()) {
                    txn.rollback();
                    LOGGER.info("prepare customer data failed." + e.getMessage());
                }
            }
        }
        
        if (productRepo.count() == 0) {
            EntityTransaction txn = em.getTransaction();
            try {
                txn.begin();
                for (int i = 0; i < 3; i++) {
                    Product p = new Product();
                    p.setName("product_" + i);
                    productRepo.save(p);
                }
                txn.commit();
                LOGGER.info("prepare customer data done.");
            } catch (Exception e) {
                if (txn.isActive()) {
                    txn.rollback();
                    LOGGER.info("prepare customer data failed."  + e.getMessage());
                }
            }
        }
    }
    
    private static void createOrder(EntityManager em) {
        BasicRepository<Order> orderRepo = new BasicRepository<Order>(em);
        BasicRepository<Customer> customerRepo = new BasicRepository<Customer>(em);
        BasicRepository<Product> productRepo = new BasicRepository<Product>(em);
        
        List<Customer> customers = customerRepo.findAll();
        List<Product> products = productRepo.findAll();
        
        Random rand = new Random();
        
        Order order = new Order();
        order.setCreatedTime(new DateTime());
        order.setCustomer(customers.get(rand.nextInt(customers.size())));
        OrderLine line1 = new OrderLine();
        line1.setParent(order);
        line1.setProduct(products.get(rand.nextInt(products.size())));
        line1.setPrice(new BigDecimal(rand.nextInt(50)));
        line1.setQuantity(new BigDecimal(rand.nextInt(10)));
        order.getLines().add(line1);
        OrderLine line2 = new OrderLine();
        line2.setParent(order);
        line2.setProduct(products.get(rand.nextInt(products.size())));
        line2.setPrice(new BigDecimal(rand.nextInt(50)));
        line2.setQuantity(new BigDecimal(rand.nextInt(10)));
        order.getLines().add(line2);
        BigDecimal amount = BigDecimal.ZERO;
        for (OrderLine line: order.getLines()) {
            amount = amount.add(line.getPrice().multiply(line.getQuantity()));
        }
        order.setAmount(amount);
        
        EntityTransaction txn = em.getTransaction();
        try {
            txn.begin();
            orderRepo.save(order);
            txn.commit();
            LOGGER.info("create order done.");
        } catch (Exception e) {
            if (txn.isActive()) {
                txn.rollback();
                LOGGER.info("create order failed."  + e.getMessage());
            }
        }
        
    }
}
