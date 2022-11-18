package com.lbs.lookbooksite.controller;

import com.lbs.lookbooksite.domain.Member;
import com.lbs.lookbooksite.dto.order.OrderProceeding;
import com.lbs.lookbooksite.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/proceeding")
    public String proceedingPage(OrderProceeding orderProceeding,Model model) {
        model.addAttribute("proceed",orderProceeding);
        return"/member/order/orderProceeding_page";
    }


    @PostMapping("/proceeding-cart")
    public String proceedingPage(OrderProceeding orderProceeding, RedirectAttributes re, @AuthenticationPrincipal Member member) {
        orderProceeding.setMemberId(member.getMemberId());
        re.addFlashAttribute("orderProceeding",orderProceeding);

        return "redirect:/order/proceeding";
    }


}
