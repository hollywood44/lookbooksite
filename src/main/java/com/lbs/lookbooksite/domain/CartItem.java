package com.lbs.lookbooksite.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cart_item_tbl")
@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne(targetEntity = Cart.class)
    @JoinColumn(name = "cartId")
    private Cart cartId;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "productId")
    private Product productId;

    private int itemCount;

    //===============================비즈니스 로직===============================//

    public void setCartId(Cart cartId) {
        this.cartId = cartId;
    }
    public void changeItemCount(int changingCount){
        this.itemCount = changingCount;
    }


}
