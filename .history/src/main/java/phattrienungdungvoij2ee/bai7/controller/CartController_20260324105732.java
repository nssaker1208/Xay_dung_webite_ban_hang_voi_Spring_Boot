package phattrienungdungvoij2ee.bai7.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import phattrienungdungvoij2ee.bai7.service.CartService;


@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("cartItems", cartService.getItems());
        model.addAttribute("total", cartService.getTotal());
        return "cart/list";
    }

    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable int id, HttpSession session) {
        // Kiểm tra xem đã đăng nhập chưa
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login"; // Chưa đăng nhập thì đuổi ra trang login
        }
        cartService.addToCart(id);
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam int productId, @RequestParam int quantity) {
        cartService.updateQuantity(productId, quantity);
        return "redirect:/cart";
    }

    @GetMapping("/remove/{id}")
    public String removeFromCart(@PathVariable int id) {
        cartService.removeFromCart(id);
        return "redirect:/cart";
    }

    @GetMapping("/clear")
    public String clearCart() {
        cartService.clear();
        return "redirect:/cart";
    }

    @PostMapping("/order")
    public String checkout(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        // Truyền user đang đăng nhập vào hàm checkout để lưu DB
        cartService.checkout(user);
        return "redirect:/cart/success"; // Giả sử bạn có trang thông báo thành công
    }
}
