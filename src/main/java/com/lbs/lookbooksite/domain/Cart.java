package com.lbs.lookbooksite.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart_tbl")
@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToOne(targetEntity = Member.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member memberId;

    @Builder.Default
    @OneToMany(mappedBy = "cartId",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<CartItem> cartItems = new ArrayList<>();

    //===============================비즈니스 로직===============================//

    public void addItem(CartItem item){
        item.setCartId(this);
        this.cartItems.add(item);
    }

    public void setMember(Member member) {
        this.memberId = member;
    }


}
