package com.example.web;

import com.example.po.Comment;
import com.example.po.User;
import com.example.service.BlogService;
import com.example.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comment(@PathVariable("blogId") Long blogId, Model model) {
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        Long id = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(id));
        User user = (User) session.getAttribute("user");
        if (user != null) {
            comment.setAdminComment(true);
            comment.setAvatar(user.getAvatar());
            //comment.setNickname(user.getNickname());
        } else {
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + id;
    }
}
