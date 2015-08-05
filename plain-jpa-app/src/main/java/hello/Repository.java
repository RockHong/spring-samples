package hello;

public interface Repository<T> {
    T save(T entity);
    
    T find(Long primaryKey);
    
    Iterable<T> findAll();
    
    boolean exists(Long primaryKey);
    
    Long count();
    
    void delete(T entity);
}
