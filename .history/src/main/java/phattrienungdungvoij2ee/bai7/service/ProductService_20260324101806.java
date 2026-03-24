package phattrienungdungvoij2ee.bai7.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import phattrienungdungvoij2ee.bai7.model.Product;
import phattrienungdungvoij2ee.bai7.repository.ProductRepository;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> GetSearchProducts(String key) {
        return productRepository.findByNameContainingIgnoreCase(key);
    }

    public Page<Product> getProductsByPage(int page, int pageSize) {
        return productRepository.findAll(PageRequest.of(page, pageSize));
    }

    // Lấy sản phẩm theo ID để hiển thị lên form Edit
    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    // Xóa sản phẩm
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    // BỔ SUNG: Hàm tổng hợp xử lý cả phân trang, sắp xếp và lọc danh mục
    public Page<Product> getAllProductsWithFilterAndSort(int page, int pageSize, String sortParam, Integer categoryId) {
        // Xử lý hướng sắp xếp (tăng dần / giảm dần theo giá)
        Sort sort = Sort.unsorted();
        if (sortParam != null && !sortParam.isEmpty()) {
            if (sortParam.equals("asc")) {
                sort = Sort.by("price").ascending();
            } else if (sortParam.equals("desc")) {
                sort = Sort.by("price").descending();
            }
        }

        // Tạo đối tượng Pageable kết hợp trang, số lượng và sắp xếp
        PageRequest pageRequest = PageRequest.of(page, pageSize, sort);

        // Nếu có chọn danh mục thì lọc theo danh mục, ngược lại lấy tất cả
        if (categoryId != null) {
            return productRepository.findByCategoryId(categoryId, pageRequest);
        }
        return productRepository.findAll(pageRequest);
    }
}
