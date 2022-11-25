package com.module.blog.service;

import com.module.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long memberId, long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getCommentById(Long postId, Long commentId);

    CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest);

    void deleteComment(Long postId, Long commentId);
}
