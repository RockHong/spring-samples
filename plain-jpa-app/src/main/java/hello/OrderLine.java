package hello;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ORDER_LINE")
public class OrderLine {
    @SequenceGenerator(name = "ORDER_LINE_SEQ", sequenceName = "ORDER_LINE_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "ORDER_LINE_SEQ", strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "ORDER_ID", nullable = false, updatable = false)
    private Order parent;
    
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;
    
    @Column(name = "QUANTITY")
    private BigDecimal quantity;
    
    @Column(name = "PRICE")
    private BigDecimal price;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Order getParent() {
        return parent;
    }
    
    public void setParent(Order order) {
        parent = order;
    }
    
    public Product getProduct() {
        return product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
    
    public BigDecimal getQuantity() {
        return quantity;
    }
    
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
