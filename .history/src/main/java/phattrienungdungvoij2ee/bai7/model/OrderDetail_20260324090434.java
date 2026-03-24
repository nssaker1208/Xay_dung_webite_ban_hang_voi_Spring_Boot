package phattrienungdungvoij2ee.bai7.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "order_details") // Thêm tên bảng rõ ràng để tránh lỗi cú pháp SQL ở một số database
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double price;

    private int quantity;

    // Quan hệ N-1: Nhiều chi tiết đơn hàng tham chiếu đến 1 sản phẩm
    @ManyToOne
    @JoinColumn(name = "product_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Product product;

    // Quan hệ N-1: Nhiều chi tiết đơn hàng tham chiếu đến 1 đơn hàng
    @ManyToOne
    @JoinColumn(name = "order_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Order order;
}