package com.module.blog.service.impl;

import com.module.blog.entity.board.Comment;
import com.module.blog.entity.board.Post;
import com.module.blog.exception.AppApiException;
import com.module.blog.exception.ResourceNotFoundException;
import com.module.blog.payload.CommentDto;
import com.module.blog.repository.CommentRepository;
import com.module.blog.repository.PostRepository;
import com.module.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    MemberRepository memberRepository;
    CommentRepository commentRepository;
    PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, MemberRepository memberRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public CommentDto createComment(long memberId, long postId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);

        //retrieve member entity by id
        Member member = memberRepository.findById(memberId).orElseThrow(()-> new ResourceNotFoundException("Member", "Member ID", memberId));

        //retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post ID", postId));

        //set post to comment
        comment.updateComment(member, post);

        // comment entity to db
        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
        // retrieve comments by postId
        List<Comment> comments = commentRepository.findByPostId(postId);

        // convert list of comment entities to list of comment dto's
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new AppApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new AppApiException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }

        comment.updateCommentBody(commentRequest);

        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new AppApiException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }

        commentRepository.delete(comment);
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = CommentDto.builder()
                .id(comment.getId())
                .name(comment.getMember().getMemberName())
                .body(comment.getBody())
                .createdTime(comment.getCreatedDate())
                .modifiedTime(comment.getModifiedDate())
                .build();

        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = Comment.builder()
                .id(commentDto.getId())
                .body(commentDto.getBody())
                .build();

        return comment;
    }
}
