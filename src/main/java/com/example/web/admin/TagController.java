package com.example.web.admin;

import com.example.po.Tag;
import com.example.po.Type;
import com.example.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String list(@PageableDefault(size=5, sort={"id"}, direction = Sort.Direction.ASC) Pageable pageable,
                       Model model) {
        model.addAttribute("page", tagService.listTag(pageable));
        return "/admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable("id") Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "Deleting tag succeed");
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {
        Tag duplicate = tagService.getTagByName(tag.getName());
        if (duplicate != null) {
            result.rejectValue("name", "nameError", "Tag name already exists");
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag t = tagService.saveTag(tag);
        if (t == null) {
            attributes.addFlashAttribute("message", "Adding new tag failed");
        } else {
            attributes.addFlashAttribute("message", "Adding new tag succeed");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result, @PathVariable("id") Long id, RedirectAttributes attributes) {
        Tag duplicate = tagService.getTagByName(tag.getName());
        if (duplicate != null) {
            result.rejectValue("name", "nameError", "Tag name already exists");
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag t = tagService.updateTag(id, tag);
        if (t == null) {
            attributes.addFlashAttribute("message", "Updating new tag failed");
        } else {
            attributes.addFlashAttribute("message", "Updating new tag succeed");
        }
        return "redirect:/admin/tags";
    }
}
