package phattrienungdungvoij2ee.bai7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phattrienungdungvoij2ee.bai7.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    // Hàm hỗ trợ tìm user bằng tên đăng nhập
    User findByUsername(String username);
}