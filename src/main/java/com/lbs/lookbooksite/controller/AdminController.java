package com.lbs.lookbooksite.controller;

import com.lbs.lookbooksite.configs.FileManager;
import com.lbs.lookbooksite.dto.lookbook.LookbookDto;
import com.lbs.lookbooksite.dto.order.OrderDto;
import com.lbs.lookbooksite.dto.styleTag.StyleTagDto;
import com.lbs.lookbooksite.service.LookBookService;
import com.lbs.lookbooksite.service.OrderService;
import com.lbs.lookbooksite.service.StyleTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final OrderService orderService;
    private final LookBookService lookBookService;
    private final StyleTagService styleTagService;
    private final FileManager fileManager;

    @GetMapping
    public String adminMainPage() {
        return "/admin/admin_page";
    }

    @GetMapping("/style-tag")
    public String styleTagManagePage(Model model) {
        StyleTagDto returnDto = styleTagService.getAllStyleTags();
        model.addAttribute("tags", returnDto);

        return "/admin/tag/styleTagManage_page";
    }

    @GetMapping("/product")
    public String productManagePage() {

        return "/admin/product/productManage_page";
    }

    @GetMapping("/order")
    public String orderManagePage(Model model,@RequestParam(value = "orderStatus", defaultValue = "ready")String orderStatus,@RequestParam(value = "page",defaultValue = "1")int page) {
        page = page-1;

        Page<OrderDto> orderList = orderService.getAllOrderCaseByStatus(orderStatus, page);
        model.addAttribute("orderList", orderList);
        model.addAttribute("maxPage",10);

        return "/admin/order/orderManage_page";
    }

    @GetMapping("/order/processing")
    public String orderProcessingPage(Model model, @RequestParam("orderId")String orderId) {
        OrderDto order = orderService.getForProcessingOrder(orderId);
        model.addAttribute("order", order);
        return "/admin/order/orderProcessing_page";
    }

    @GetMapping("/lookbook")
    public String lookbookManagePage(Model model,
                                     @RequestParam(value = "styleTag", defaultValue = "all") String styleTag,
                                     @RequestParam(value="page",defaultValue = "0") int page) {
        Page<LookbookDto> allLookbook = lookBookService.getAllLookbook(styleTag,page);
        Map<String,String> allTag = styleTagService.getAllStyleTags().getStyleTag();

        model.addAttribute("allTag", allTag);
        model.addAttribute("allLookbook", allLookbook);

        return "/admin/lookbook/lookbookManage_page";
    }



    @PostMapping("/style-tag")
    public String addStyleTag(StyleTagDto tag, RedirectAttributes redirectAttributes) {

        System.out.println(tag);

        int checkStatus = fileManager.fileCheckIsNullOrNotImg(tag.getUrl());

        if (tag.getTag().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "제목은 필수 사항입니다.");
            return "redirect:/admin/style-tag";
        }
        else if (checkStatus == 0) {
            redirectAttributes.addFlashAttribute("error", "이미지는 필수 사항입니다.");
            return "redirect:/admin/style-tag";
        }
        else if (tag.getUrlName().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "이미지명은 필수 사항입니다.");
            return "redirect:/admin/style-tag";
        }

        if (checkStatus == 1) {
            styleTagService.addStyleTag(tag);
        }

        return "redirect:/admin/style-tag";
    }

    @PostMapping("/order/process")
    public String orderProcessed(@RequestParam("orderId")String orderId,@RequestParam("orderStatus")String orderStatus) {

        switch (orderStatus) {
            case "complete":
                orderService.orderCompleteForAdmin(orderId);
                break;
            case "cancel":
                orderService.orderCancelForAdmin(orderId);
                break;
        }

        return "redirect:/admin/order/processing?orderId=" + orderId;
    }
}
