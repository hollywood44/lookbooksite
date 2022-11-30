package com.lbs.lookbooksite.controller;

import com.lbs.lookbooksite.dto.lookbook.LookbookDto;
import com.lbs.lookbooksite.dto.product.ProductDto;
import com.lbs.lookbooksite.service.LookBookService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/lookbook")
@RequiredArgsConstructor
public class LookbookController {

    private final LookBookService lookBookService;

    @GetMapping("/home")
    public String lookbookHomePage(Model model,
                                   @RequestParam(value = "styleTag", defaultValue = "all") String styleTag,
                                   @RequestParam(value="page",defaultValue = "0") int page) {
        Page<LookbookDto> allLookbook = lookBookService.getAllLookbook(styleTag,page);
        model.addAttribute("allLookbook", allLookbook);
        return "/member/lookbook/lookbookHome_page";
    }

    @GetMapping("/detail/{lookbookId}")
    public String lookbookDetailPage(Model model,@PathVariable("lookbookId") Long lookbookId) {
        LookbookDto detail = lookBookService.getLookbookDetail(lookbookId);
        model.addAttribute("detail", detail);
        return "/member/lookbook/lookbookDetail_page";
    }

    @GetMapping("/post")
    public String lookbookPostPage(LookbookDto lookbookDto) {
        return "/member/lookbook/lookbookPost_page";
    }

    @GetMapping("/modify/{lookbookId}")
    public String lookbookModifyPage(Model model,@PathVariable("lookbookId") Long lookbookId) {
        LookbookDto lookbookDto = lookBookService.getLookbookDetail(lookbookId);
        lookbookDto.setDescription(lookbookDto.getDescription().replace("<br>","\n"));
        model.addAttribute("lookbookDto",lookbookDto);

        return "/member/lookbook/lookbookModify_page";
    }

    @GetMapping("/delete-img")
    public String lookbookImageDelete(@RequestParam("imageId") Long imageId,@RequestParam("lookbookId") Long lookbookId) {
        lookBookService.deleteLookbookImg(imageId);

        return "redirect:/lookbook/modify/"+lookbookId;
    }


    @PostMapping("/post")
    public String lookbookPosting(@Valid LookbookDto lookbookDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/member/lookbook/lookbookPost_page";
        }

        // 들어온 파일이 이미지가 아니거나, 비어있을 경우 체크
        int checkFileIsNull = 0;
        for (MultipartFile img : lookbookDto.getGetImages()) {
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
        lookbookDto.setDescription(lookbookDto.getDescription().replace("\n","<br>"));

        if (checkFileIsNull == 0) {
            return "/member/lookbook/lookbookPost_page";
        } else {
            lookBookService.postingWithImg(lookbookDto);
        }

        return "redirect:/lookbook/home";
    }

    @PostMapping("/modify")
    public String lookbookModify(@Valid LookbookDto lookbookDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/lookbook/modify/"+lookbookDto.getLookbookId();
        }

        // 들어온 파일이 이미지가 아니거나, 비어있을 경우 체크
        int checkFileIsNull = 0;
        for (MultipartFile img : lookbookDto.getGetImages()) {
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
        lookbookDto.setDescription(lookbookDto.getDescription().replace("\n","<br>"));

        lookBookService.modifyLookbook(lookbookDto,checkFileIsNull);

        return "redirect:/lookbook/detail/"+lookbookDto.getLookbookId();
    }

    @PostMapping("/delete")
    public String deleteLookBook(@RequestParam("lookbookId") Long lookbookId, RedirectAttributes redirectAttributes) {
        lookBookService.deleteLookbook(lookbookId);
//        redirectAttributes.addFlashAttribute("deleteMsg", lookbookId + "룩북이 삭제 되었습니다.");
        return "redirect:/lookbook/home";
    }


}
