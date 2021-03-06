package hello;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javassist.tools.reflect.Reflection;

public class MainClass {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainClass.class);
    private static final String OPT_CREATE_DB = "-create-db";
    // drop existing schema firstly, then created a new one
    private static final String OPT_DROP_CREATE_DB = "-drop-create-db";
    private static final String OPT_DROP_CREATE_DB_HB_1 = "-drop-create-db-hb-1";
    private static final String OPT_DROP_CREATE_DB_HB_2 = "-drop-create-db-hb-2";
    private static final String OPT_GEN_SCRIPT = "-gen-script";
    
    public static void main(String[] args) {
        if (args.length == 0) {
            run();
        } else if (args[0].equals(OPT_CREATE_DB)){
            createDB();
        } else if (args[0].equals(OPT_DROP_CREATE_DB)){
            dropAndCreateDB();
        }  else if (args[0].equals(OPT_DROP_CREATE_DB_HB_1)){
            dropAndCreateDBViaHibernate1();
        }  else if (args[0].equals(OPT_DROP_CREATE_DB_HB_2)){
            dropAndCreateDBViaHibernate2();
        }  else if (args[0].equals(OPT_GEN_SCRIPT)){
            generateSript();
        }
    }
    
    private static void createDB() {
        /*
         * simply call Persistence.generateSchema() will create schema, but main() method
         * never returns. some threads (relative to db connection/connect pool) will hang in there
         * and never terminate. those never-terminate threads look like below,
         * Daemon Thread [Abandoned connection cleanup thread] (Running)  
         * Thread [pool-1-thread-1] (Running)   
         * Thread [DestroyJavaVM] (Running)
         */
        Properties properties = new Properties();
        properties.put("javax.persistence.schema-generation.database.action", "create");
        properties.put("javax.persistence.schema-generation.create-source", "metadata");
        properties.put("javax.persistence.schema-generation.drop-source", "metadata");
        Persistence.generateSchema("simplePU", null);
    }
    
    private static void dropAndCreateDB() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        
        Properties properties = new Properties();
        properties.put("javax.persistence.schema-generation.database.action", "drop-and-create");
        properties.put("javax.persistence.schema-generation.create-source", "metadata");
        properties.put("javax.persistence.schema-generation.drop-source", "metadata");
        Persistence.generateSchema("simplePU", properties);
        
        /*
         * it's said calling SessionFactory::close() can help terminate never-terminate threads
         * mentioned above.
         * however, it's not true in my case.
         */
        sf.close();
    }
    
    /*
     * this method has no problem of hanging threads like dropAndCreateDB() and createDB()
     */
    private static void dropAndCreateDBViaHibernate1() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        Configuration cfg = new Configuration().setProperties(properties);
        // can also use reflection to not (hard-codely) call addAnnotatedClass() for every entity
        cfg.addAnnotatedClass(Customer.class);
        cfg.addAnnotatedClass(Order.class);
        cfg.addAnnotatedClass(OrderLine.class);
        cfg.addAnnotatedClass(Product.class);
        String[] scripts = cfg.generateSchemaCreationScript(Dialect.getDialect(cfg.getProperties()));
        LOGGER.info("len of scripts " + scripts.length);
        for (String sql: scripts) {
            LOGGER.info(sql);            
        }
        // org.hibernate.engine.jdbc.internal.FormatStyle can help do formatting
        // it has a method getFormatter(), which return Formatter
    }
    
    private static void dropAndCreateDBViaHibernate2() {
        Properties properties = new Properties();
        // even though here we just use SchemaExport to generate schema script,
        // we still need to set 'connection properties'
        properties.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        properties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/simplejpadb");
        properties.setProperty("hibernate.connection.username", "root");
        properties.setProperty("hibernate.connection.password", "");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        Configuration cfg = new Configuration().setProperties(properties);
        //cfg.addResource("META-INF/persistence.xml"); // not work...
        //cfg.addPackage("hello"); // not work...
        cfg.addAnnotatedClass(Customer.class);
        cfg.addAnnotatedClass(Order.class);
        cfg.addAnnotatedClass(OrderLine.class);
        cfg.addAnnotatedClass(Product.class);
        SchemaExport export = new SchemaExport(cfg);
        export.setOutputFile("schema-creation-SchemaExport.txt");
        export.setFormat(false);
        export.execute(false, true, false, true);
    }
    
    private static void generateSript() {
        Properties properties = new Properties();
        properties.put("javax.persistence.schema-generation.scripts.action", "create");
        properties.put("javax.persistence.schema-generation.scripts.create-target", "createDll.txt");
            
        Persistence.generateSchema("simplePU", properties);
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
        BasicRepository<Customer> customerRepo = new BasicRepository<Customer>(em, Customer.class);
        BasicRepository<Product> productRepo = new BasicRepository<Product>(em, Product.class);
        
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
        BasicRepository<Order> orderRepo = new BasicRepository<Order>(em, Order.class);
        BasicRepository<Customer> customerRepo = new BasicRepository<Customer>(em, Customer.class);
        BasicRepository<Product> productRepo = new BasicRepository<Product>(em, Product.class);
        
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
