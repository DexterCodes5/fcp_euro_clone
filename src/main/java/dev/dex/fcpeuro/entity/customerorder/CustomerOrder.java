package dev.dex.fcpeuro.entity.customerorder;

import dev.dex.fcpeuro.model.customerorder.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String payment;
    private Double subtotal;
    private Double shipping;
    private Double tax;
    private Double total;
    private Integer userId;
    @OneToMany(mappedBy = "customerOrder", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    public CustomerOrder(CustomerOrderRequest customerOrderRequest) {
        this.email = customerOrderRequest.email();
        this.payment = customerOrderRequest.payment();
        this.subtotal = customerOrderRequest.subtotal();
        this.shipping = customerOrderRequest.shipping();
        this.tax = customerOrderRequest.tax();
        this.total = customerOrderRequest.total();
        this.cartItems = customerOrderRequest
                .cartItems()
                .stream()
                .map(cartItemRequest -> new CartItem(null, this, cartItemRequest.id(), cartItemRequest.qty()))
                .toList();
        this.address = customerOrderRequest.address();
        this.delivery = new Delivery(null,
                customerOrderRequest.delivery().receiveTextNotification(),
                customerOrderRequest.delivery().price(),
                customerOrderRequest.delivery().receiveDate(),
                customerOrderRequest.address());
    }
}
