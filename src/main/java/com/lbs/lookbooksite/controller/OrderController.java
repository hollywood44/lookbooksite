package com.lbs.lookbooksite.controller;

import com.lbs.lookbooksite.domain.Member;
import com.lbs.lookbooksite.dto.board.BoardDto;
import com.lbs.lookbooksite.dto.order.OrderDto;
import com.lbs.lookbooksite.dto.order.OrderItemDto;
import com.lbs.lookbooksite.dto.order.OrderProceeding;
import com.lbs.lookbooksite.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/proceeding")
    public String proceedingPage(OrderProceeding orderProceeding, OrderDto orderDto, Model model) {
        model.addAttribute("proceed",orderProceeding);
        return"/member/order/orderProceeding_page";
    }

    @GetMapping("/myOrder")
    public String myOrderPage(Model model,@AuthenticationPrincipal Member loginedMember,@RequestParam(value = "page", defaultValue = "1") int page) {
        page = page -1;

        Page<OrderDto> paging = orderService.getMyOrder(loginedMember,page);

        model.addAttribute("paging", paging);
        model.addAttribute("maxPage",5);
        model.addAttribute("totalOrder", paging.getTotalElements());

        return "member/order/myOrder_page";
    }


    @PostMapping("/proceeding-cart")
    public String proceedingPage(OrderProceeding orderProceeding, RedirectAttributes re, @AuthenticationPrincipal Member member) {
        orderProceeding.setMemberId(member.getMemberId());
        re.addFlashAttribute("orderProceeding",orderProceeding);

        return "redirect:/order/proceeding";
    }

    @PostMapping("/putOrder")
    public String putOrder(OrderDto orderDto,@AuthenticationPrincipal Member member,RedirectAttributes redirectAttributes) {

        String status = orderService.putOrder(orderDto,member);
        if (status != null) {
            redirectAttributes.addFlashAttribute("orderSuccessMsg", "주문이 정상적으로 완료되었습니다.");
        }
        return "redirect:/order/myOrder";
    }


}
