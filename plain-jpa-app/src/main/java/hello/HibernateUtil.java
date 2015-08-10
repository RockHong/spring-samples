package hello;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateUtil.class);
    private static final SessionFactory sf = buildSessionFactoryFromProperties();
    
    private static SessionFactory buildSessionFactoryFromXMLConfig() {
        try {
            return new Configuration().configure().buildSessionFactory(
                    new StandardServiceRegistryBuilder().build());
        } catch (Throwable ex) {
            LOGGER.error("failed to get session factory.");
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    private static SessionFactory buildSessionFactoryFromProperties() {
        try {
            Properties properties = new Properties();
            // using key beginning with "javax.persistence.*" or ""hibernate.*" are both ok
            properties.setProperty("javax.persistence.jdbc.drive", "com.mysql.jdbc.Driver");
            properties.setProperty("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/simplejpadb");
            properties.setProperty("javax.persistence.jdbc.user", "root");
            properties.setProperty("javax.persistence.jdbc.password", "");
            //properties.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
            //properties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/simplejpadb");
            //properties.setProperty("hibernate.connection.username", "root");
            //properties.setProperty("hibernate.connection.password", "");
            properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
            return new Configuration().setProperties(properties).buildSessionFactory(
                    new StandardServiceRegistryBuilder().applySettings(properties).build());
        } catch (Throwable ex) {
            LOGGER.error("failed to get session factory.");
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sf;
    }
}
