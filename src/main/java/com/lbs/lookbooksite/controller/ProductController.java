package com.lbs.lookbooksite.controller;

import com.lbs.lookbooksite.dto.product.ProductDto;
import com.lbs.lookbooksite.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.List;


@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public String productTestPage() {
        return "productTest";
    }

    @GetMapping("/list")
    public String productListTestPage(Model model) {
        List<ProductDto> allProduct = productService.getAllProductList();
        model.addAttribute("allProduct",allProduct);
        return "productListTest";
    }

    @PostMapping("/upload")
    public String upload(ProductDto dto) {

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

        if (checkFileIsNull == 0) {
            productService.uploadProductWithOutImg(dto);
        } else {
            productService.uploadProductWithImg(dto);
        }

        return "redirect:/product/list";
    }
}
