package com.lbs.lookbooksite.controller;

import com.lbs.lookbooksite.dto.product.ProductDto;
import com.lbs.lookbooksite.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.util.List;


@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 상품 리스트보기 페이지
    @GetMapping("/list")
    public String productListTestPage(Model model,@RequestParam(value = "page",defaultValue = "1")int page) {
        page = page-1;
        Page<ProductDto> allProduct = productService.getAllProductList(page);
        model.addAttribute("allProduct",allProduct);
        model.addAttribute("maxPage",10);

        return "/member/product/productList_page";
    }

    // 상품 상세보기 페이지
    @GetMapping("/detail/{productId}")
    public String productDetailPage(Model model, @PathVariable("productId") String productId) {
        ProductDto product = productService.getProduct(productId);
        model.addAttribute("product",product);
        return "member/product/productDetail_page";
    }
    // 업로드 페이지
    @GetMapping("/upload")
    public String postPage(ProductDto productDto) {
        return "/admin/product/productPost_page";
    }

    // 수정 페이지
    @GetMapping("/modify/{productId}")
    public String productModifyPage(Model model,@PathVariable("productId") String productId) {
        ProductDto productDto = productService.getProduct(productId);
        productDto.setDescription(productDto.getDescription().replace("<br>","\n"));
        model.addAttribute("productDto",productDto);

        return "admin/product/productModify_page";
    }

    // 상품 업로드
    @PostMapping("/upload")
    public String upload(@Valid ProductDto productDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/product/productPost_page";
        }

        // 들어온 파일이 이미지가 아니거나, 비어있을 경우 체크
        int checkFileIsNull = 0;
        for (MultipartFile img : productDto.getGetImages()) {
            File checkfile = new File(img.getOriginalFilename());
            String type = null;
            try {
                type = Files.probeContentType(checkfile.toPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (img.isEmpty() || !type.startsWith("image")) {
                checkFileIsNull = 0;
            } else {
                checkFileIsNull = 1;
            }
        }
        productDto.setDescription(productDto.getDescription().replace("\n","<br>"));

        if (checkFileIsNull == 0) {
            productService.uploadProductWithOutImg(productDto);
        } else {
            productService.uploadProductWithImg(productDto);
        }

        return "redirect:/product/list";
    }


    // 상품 수정
    @PostMapping("/modify")
    public String modify(@Valid ProductDto dto,BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/product/modify/"+dto.getProductId();
        }
        // 들어온 파일이 이미지가 아니거나, 비어있을 경우 체크
        int checkFileIsNull = 0;
        for (MultipartFile img : dto.getGetImages()) {
            File checkfile = new File(img.getOriginalFilename());
            String type = null;
            try {
                type = Files.probeContentType(checkfile.toPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (img.isEmpty() || !type.startsWith("image")) {
                checkFileIsNull = 0;
            } else {
                checkFileIsNull = 1;
            }
        }

        dto.setDescription(dto.getDescription().replace("\n","<br>"));

        productService.modifyProduct(dto,checkFileIsNull);

        return "redirect:/product/detail/"+dto.getProductId();
    }

    // 상품 삭제
    @PostMapping("/delete")
    public String deleteProduct(@RequestParam("productId") String productID, RedirectAttributes redirectAttributes) {
        productService.deleteProduct(productID);

        return "redirect:/product/list";
    }
}
