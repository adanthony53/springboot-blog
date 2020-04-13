package com.example.web;

import com.example.po.Blog;
import com.example.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ArchiveController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archive")
    public String archive(Model model) {
        Map<String, List<Blog>> map = blogService.archiveBlog();
        model.addAttribute("blogCount", blogService.count());
        model.addAttribute("archiveMap", map);
        return "archive";
    }
}
