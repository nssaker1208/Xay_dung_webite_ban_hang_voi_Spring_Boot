package phattrienungdungvoij2ee.bai7.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import phattrienungdungvoij2ee.bai7.model.Product;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Phục vụ tìm kiếm
    List<Product> findByNameContainingIgnoreCase(String name);

    // Phục vụ phân trang
    Page<Product> findAll(Pageable pageable);

    
    Page<Product> findByCategoryId(Integer categoryId, Pageable pageable);
}
