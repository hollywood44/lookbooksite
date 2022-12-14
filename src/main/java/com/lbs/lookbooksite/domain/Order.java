package com.lbs.lookbooksite.domain;

import com.lbs.lookbooksite.domain.timeEntities.OrderTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_tbl")
@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends OrderTimeEntity {

    @Id
    private String orderId;

    @ManyToOne(targetEntity = Member.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member memberId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(length = 100)
    private String address;

    @Column(length = 100)
    private String addressDetail;

    @Column(length = 20)
    private String receiverName;

    private int totalPrice;

    @Builder.Default
    @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    public enum OrderStatus {
        Ready,Cancel,Shipping,Complete
    }


    //===============================비즈니스 로직===============================//

    public void addItem(OrderItem item){
        item.setOrderId(this);
        this.orderItems.add(item);
    }

    public void changeOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

}
