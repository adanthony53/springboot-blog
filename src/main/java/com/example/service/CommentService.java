package com.example.service;

import com.example.po.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long id);

    Comment saveComment(Comment comment);
}
