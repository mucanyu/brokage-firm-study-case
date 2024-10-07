package com.mucanyu.brokage.order.model;

import com.mucanyu.brokage.order.constant.OrderSide;
import com.mucanyu.brokage.order.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CUSTOMER_ID", nullable = false)
    private Long customerId;

    @Column(name = "ASSET_NAME", nullable = false)
    private String assetName;

    @Column(name = "SIZE", nullable = false)
    private BigDecimal size;

    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @Column(name = "CREATE_DATE", nullable = false)
    private LocalDateTime createDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "SIDE", nullable = false)
    private OrderSide side;


    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private OrderStatus status;
}

