package phattrienungdungvoij2ee.bai7.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String addToCart(@PathVariable int id) {
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
    public String checkout(Model model) {
        // Tài liệu yêu cầu xử lý logic đặt hàng (checkout) trong CartService
        cartService.checkout();

        // Trả về trang thông báo "Đặt hàng thành công" theo yêu cầu của bài
        return "cart/checkout-success";
    }
}
