package phattrienungdungvoij2ee.bai7.model;

import lombok.Data;

@Data
public class CartItem {
    private Integer id;
    private String name;
    private String image;
    private long price;
    private int quantity;
}
