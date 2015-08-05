package hello;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.joda.time.DateTime;

@Entity
// @Table can be omitted, if defaults are ok. however, specify 'name' of @Table
// is better, so that the mapping between entity and table wouldn't break 
// if we refactor Order class name later on
@Table(name = "ORDER")
public class Order {
    @SequenceGenerator(name = "ORDER_SEQ", sequenceName = "ORDER_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "ORDER_SEQ", strategy = GenerationType.SEQUENCE)
    private Long id;
    
    // do 'optional = false' in @ManyToOne and 'nullable = false' in @JoinColumn
    // have the same effect?
    @ManyToOne(optional = false)
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private Customer customer;
    
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    
    /*
     * https://docs.oracle.com/javaee/6/tutorial/doc/bnbqa.html
     * for 'date', jpa supports java.util.Date, java.util.Calendar, java.sql.Date, 
     * java.sql.Time, java.sql.TimeStamp. 
     * 
     * http://www.joda.org/joda-time/
     * it's said joda time the best choice before java 1.8.
     * 
     * since the app uses java 1.7, and also it needs time for jpa to support java
     * 1.8's 'date class', so we use joda time (via a converter) in this app.
     */
    @Column(name = "CREATED_TIME")
    @Convert(converter = JodaTimeConverter.class)
    private DateTime createdTime;
    
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private final List<OrderLine> lines = new ArrayList<OrderLine>();
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public DateTime getCreatedTime() {
        return createdTime;
    }
    
    public void setCreatedTime(DateTime createdTime) {
        this.createdTime = createdTime;
    }
    
    public List<OrderLine> getLines() {
        return lines;
    }
}
