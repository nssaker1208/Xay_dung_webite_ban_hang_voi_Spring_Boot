package phattrienungdungvoij2ee.bai7.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date orderDate;

    private boolean isPaid;

    private double totalAmount;

    // Quan hệ 1-N: 1 đơn hàng có nhiều chi tiết đơn hàng
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Nhớ tạo Getter và Setter cho thuộc tính user này nhé
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}