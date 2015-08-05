package hello;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT")
public class Product {
    @SequenceGenerator(name = "PRODUCT_SEQ", sequenceName = "PRODUCT_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "PRODUCT_SEQ", strategy = GenerationType.SEQUENCE)
    private Long id;
    
    // usually if defaults of @Column is ok for you, @Column can be omitted
    // however, specifying 'name' of @Column explicitly is better, so that we
    // can refactor 'name' property of Product class later
    @Column(name = "NAME")
    private String name;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
