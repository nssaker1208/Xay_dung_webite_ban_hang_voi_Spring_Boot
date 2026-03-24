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
    public String listProducts(Model model, 
                               @RequestParam(defaultValue = "1") int page,
                               @RequestParam(required = false) String sort,
                               @RequestParam(required = false) Integer categoryId) {
        int pageSize = 5; // Giữ nguyên mỗi trang 5 sản phẩm
        
        // Gọi hàm xử lý đa chức năng từ Service
        Page<Product> productPage = productService.getAllProductsWithFilterAndSort(page - 1, pageSize, sort, categoryId);
        
        // Gửi dữ liệu ra View
        model.addAttribute("products", productPage);
        model.addAttribute("currentPage", page);
        
        // BỔ SUNG: Gửi dữ liệu cho form Lọc & Sắp xếp
        model.addAttribute("categories", categoryService.getAllCategories()); // Đổ dữ liệu vào Dropdown
        model.addAttribute("currentCategory", categoryId); // Giữ lại lựa chọn hiện tại của người dùng
        model.addAttribute("currentSort", sort);           // Giữ lại lựa chọn sắp xếp hiện tại
        
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
