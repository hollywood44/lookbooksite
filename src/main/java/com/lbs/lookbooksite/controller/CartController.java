package com.lbs.lookbooksite.controller;


import com.lbs.lookbooksite.domain.Member;
import com.lbs.lookbooksite.dto.cart.CartDto;
import com.lbs.lookbooksite.dto.cart.CartItemDto;
import com.lbs.lookbooksite.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    //<editor-fold desc="GET Request">

    @GetMapping("/my-cart")
    public String cartPage(Model model,@AuthenticationPrincipal Member loginedMember) {
        model.addAttribute("cart",cartService.getMyCart(loginedMember));
        return "/member/cart/myCart_page";
    }

    //</editor-fold>

    //<editor-fold desc="POST Request">

    @PostMapping("/add-item")
    public String addItemToCart(CartItemDto itemDto, @AuthenticationPrincipal Member loginedMember) {
        cartService.itemAddToCart(itemDto.getProductId(), itemDto.getItemCount(), loginedMember);

        return "redirect:/product/detail/"+itemDto.getProductId();
    }
    //</editor-fold>

}
