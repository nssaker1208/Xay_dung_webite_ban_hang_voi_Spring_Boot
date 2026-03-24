package phattrienungdungvoij2ee.bai7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phattrienungdungvoij2ee.bai7.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}