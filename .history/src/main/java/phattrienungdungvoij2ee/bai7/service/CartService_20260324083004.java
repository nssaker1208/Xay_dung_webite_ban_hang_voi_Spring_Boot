package phattrienungdungvoij2ee.bai7.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import phattrienungdungvoij2ee.bai7.model.CartItem;
import phattrienungdungvoij2ee.bai7.model.Order;
import phattrienungdungvoij2ee.bai7.model.OrderDetail;
import phattrienungdungvoij2ee.bai7.model.Product;
import phattrienungdungvoij2ee.bai7.repository.OrderRepository;
import phattrienungdungvoij2ee.bai7.repository.ProductRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@SessionScope
public class CartService {
    private List<CartItem> items = new ArrayList<>();

    @Autowired
    private ProductRepository productRepository;

    // BỔ SUNG: Inject OrderRepository để lưu đơn hàng
    @Autowired
    private OrderRepository orderRepository;

    public List<CartItem> getItems() {
        return items;
    }

    public void addToCart(int productId) {
        Product findProduct = productRepository.findById(productId).orElse(null);
        if (findProduct == null) {
            return;
        }

        items.stream().filter(item -> item.getId() == findProduct.getId())
                .findFirst().ifPresentOrElse(
                        item -> item.setQuantity(item.getQuantity() + 1),
                        () -> {
                            CartItem newItem = new CartItem();
                            newItem.setId(productId);
                            newItem.setName(findProduct.getName());
                            newItem.setImage(findProduct.getImage());
                            newItem.setPrice(findProduct.getPrice());
                            newItem.setQuantity(1);
                            items.add(newItem);
                        });
    }

    public void updateQuantity(int productId, int quantity) {
        items.stream()
                .filter(item -> item.getId() == productId)
                .findFirst()
                .ifPresent(item -> item.setQuantity(quantity));
    }

    public void removeFromCart(int productId) {
        items.removeIf(item -> item.getId().equals(productId));
    }

    public void clear() {
        items.clear();
    }

    public double getTotal() {
        return items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    // BỔ SUNG: Hàm xử lý đặt hàng
    public void checkout() {
        if (items.isEmpty()) {
            return; // Giỏ hàng trống thì không làm gì cả
        }

        // Bước 1: Tạo Order (Đơn hàng) chứa thông tin chung
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setPaid(false); // Mặc định là chưa thanh toán
        order.setTotalAmount(getTotal());

        // Bước 2: Tạo OrderDetail chứa danh sách sản phẩm đã mua
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartItem item : items) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order); // Gắn chi tiết này vào đơn hàng vừa tạo

            // Tìm Product tương ứng để gán vào OrderDetail
            Product product = productRepository.findById(item.getId()).orElse(null);
            detail.setProduct(product);

            detail.setPrice(item.getPrice());
            detail.setQuantity(item.getQuantity());

            orderDetails.add(detail);
        }

        // Cập nhật danh sách chi tiết cho đơn hàng
        order.setOrderDetails(orderDetails);

        // Lưu đơn hàng vào database (nhờ CascadeType.ALL ở Entity, OrderDetail cũng sẽ
        // tự động được lưu)
        orderRepository.save(order);

        // Bước 3: Xóa giỏ hàng để chuẩn bị cho lần mua tiếp theo [cite: 1313-1314]
        clear();
    }
}