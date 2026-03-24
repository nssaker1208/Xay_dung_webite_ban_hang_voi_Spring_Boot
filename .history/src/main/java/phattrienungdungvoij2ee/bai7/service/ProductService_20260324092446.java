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

    public void saveProduct(Product product) {
        productRepository.save(product);
    }
}
