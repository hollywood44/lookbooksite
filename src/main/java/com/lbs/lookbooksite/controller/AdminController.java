package com.lbs.lookbooksite.controller;

import com.lbs.lookbooksite.configs.FileManager;
import com.lbs.lookbooksite.dto.styleTag.StyleTagDto;
import com.lbs.lookbooksite.service.StyleTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class AdminController {

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
}
