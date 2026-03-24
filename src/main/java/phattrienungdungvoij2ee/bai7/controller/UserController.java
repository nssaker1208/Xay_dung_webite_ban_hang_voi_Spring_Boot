package phattrienungdungvoij2ee.bai7.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import phattrienungdungvoij2ee.bai7.model.User;
import phattrienungdungvoij2ee.bai7.repository.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // 1. Giao diện Đăng nhập
    @GetMapping("/login")
    public String showLoginForm() {
        return "user/login";
    }

    // 2. Xử lý Đăng nhập
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
            HttpSession session, Model model) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            // Đăng nhập thành công -> Lưu vào Session
            session.setAttribute("loggedInUser", user);
            return "redirect:/products";
        }
        model.addAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
        return "user/login";
    }

    // 3. Giao diện Đăng ký
    @GetMapping("/register")
    public String showRegisterForm() {
        return "user/register";
    }

    // 4. Xử lý Đăng ký
    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, Model model) {
        if (userRepository.findByUsername(username) != null) {
            model.addAttribute("error", "Tên đăng nhập đã tồn tại!");
            return "user/register";
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password); // Thực tế nên mã hóa (Hash), nhưng test thì để text thường
        userRepository.save(newUser);
        return "redirect:/login"; // Đăng ký xong bắt đi đăng nhập
    }

    // 5. Đăng xuất
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loggedInUser"); // Xóa session
        return "redirect:/login";
    }
}