package phattrienungdungvoij2ee.bai7.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Dùng Integer để khớp với JpaRepository<Product, Integer>

    private String name;
    private String image;
    private long price; // Dùng long để khớp với khai báo trong CartItem của tài liệu

    // Mối quan hệ N-1: Nhiều sản phẩm thuộc về 1 danh mục
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
