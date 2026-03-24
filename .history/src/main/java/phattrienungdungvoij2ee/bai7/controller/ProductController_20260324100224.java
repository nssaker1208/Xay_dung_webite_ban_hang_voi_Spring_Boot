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
        int pageSize = 15;
        // Trừ 1 để Spring Data JPA hiểu đúng (người dùng thấy trang 1, code xử lý trang 0)
        Page<Product> productPage = productService.getProductsByPage(page - 1, pageSize);
        
        model.addAttribute("products", productPage);
        model.addAttribute("currentPage", page); // Lưu số trang hiện tại để dùng cho View
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
    public String showEditForm(@PathVariable int id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories()); // Load danh mục cho dropdown
        return "product/edit";
    }

    // 2. Lưu thông tin sau khi Edit
    @PostMapping("/edit")
    public String updateProduct(@ModelAttribute("product") Product product) {
        productService.saveProduct(product); // Hàm save tự động cập nhật nếu ID đã tồn tại
        return "redirect:/products";
    }

    // 3. Xử lý chức năng Delete
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
