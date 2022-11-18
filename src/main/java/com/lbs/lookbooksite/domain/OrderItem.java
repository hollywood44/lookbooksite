package com.lbs.lookbooksite.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "order_item_tbl")
@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId") // String type Fk
    private Order orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId") // String type FK
    private Product productId;

    private int itemCount;

    //===============================비즈니스 로직===============================//

    public void changeItemCount(int changingCount){
        this.itemCount = changingCount;
    }

    public void setOrderId(Order order) {
        this.orderId = order;
    }
}
