package phattrienungdungvoij2ee.bai7.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import phattrienungdungvoij2ee.bai7.model.Product;
import phattrienungdungvoij2ee.bai7.service.ProductService;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    // Chức năng phân trang
    @GetMapping
    public String listProducts(Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 15; // Kích thước trang mặc định
        Page<Product> productPage = productService.getProductsByPage(page, pageSize);
        model.addAttribute("products", productPage);
        return "product/list";
    }

    // Chức năng tìm kiếm
    @GetMapping("/search")
    public String index(Model model, String key) {
        List<Product> listProduct = productService.GetSearchProducts(key);
        model.addAttribute("products", listProduct);
        return "product/list";
    }
}
