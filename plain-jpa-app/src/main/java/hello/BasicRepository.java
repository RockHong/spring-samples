package hello;

import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicRepository<T> implements Repository<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicRepository.class);
    private EntityManager em;
    private Class<T> clazz;
    
    public BasicRepository(EntityManager em, Class<T> clazz) {
        this.em = em;
        this.clazz = clazz;
    }

    public T save(T entity) {
        if (entity != null) {
            em.persist(entity);
            LOGGER.info(clazz.getName() + "saved.");
        }
        return entity;
    }

    public T find(Long primaryKey) {
        if (primaryKey == null) {
            return null;
        } else {
            return em.find(clazz, primaryKey);
        }
    }

    public List<T> findAll() {
        String sqlStr = "SELECT e FROM " + clazz.getSimpleName() + " e";
        return em.createQuery(sqlStr, clazz).getResultList();
    }

    public boolean exists(Long primaryKey) {
        return find(primaryKey) != null;
    }

    public Long count() {
        String sqlStr = "SELECT COUNT(e) FROM " + clazz.getSimpleName() + " e";
        return (Long) em.createQuery(sqlStr, Long.class).getSingleResult();
    }

    public void delete(T entity) {
        em.remove(entity);
    }

}
