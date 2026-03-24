package phattrienungdungvoij2ee.bai7.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String listProducts(Model model, @RequestParam(defaultValue = "1") int page) {
        // Đổi pageSize từ 15 thành 5
        int pageSize = 5; 
        
        // Spring Data JPA sẽ tự động tính toán Offset dựa trên số 5 này [cite: 515, 551]
        Page<Product> productPage = productService.getProductsByPage(page - 1, pageSize);
        
        model.addAttribute("products", productPage);
        model.addAttribute("currentPage", page);
        return "product/list";
    }

    // Chức năng tìm kiếm
    @GetMapping("/search")
    public String index(Model model, String key) {
        List<Product> listProduct = productService.GetSearchProducts(key);
        model.addAttribute("products", listProduct);
        return "product/list";
    }

    @Autowired
    private phattrienungdungvoij2ee.bai7.service.CategoryService categoryService;

    // Hiển thị form thêm sản phẩm
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/add"; // Trỏ tới file add.html
    }

    // Xử lý lưu sản phẩm vào Database
    @PostMapping("/add")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.saveProduct(product);
        return "redirect:/products"; // Lưu xong quay về trang danh sách
    }

    // 1. Hiển thị form Edit
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id,
            @RequestParam(defaultValue = "1") int page,
            Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("currentPage", page); // Truyền tiếp số trang sang giao diện Edit
        return "product/edit";
    }

    // 2. Nhận tham số page từ form gửi lên để redirect cho đúng
    @PostMapping("/edit")
    public String updateProduct(@ModelAttribute("product") Product product,
            @RequestParam int page) {
        productService.saveProduct(product);
        // Sau khi lưu xong, quay về đúng trang cũ
        return "redirect:/products?page=" + page;
    }

    // 3. Xử lý chức năng Delete
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id, @RequestParam(defaultValue = "1") int page) {
        productService.deleteProduct(id);
        // Trả về đúng trang mà người dùng vừa thao tác
        return "redirect:/products?page=" + page;
    }
}
