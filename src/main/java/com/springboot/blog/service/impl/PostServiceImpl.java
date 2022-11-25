package com.springboot.blog.service.impl;

import com.springboot.blog.entity.board.Comment;
import com.springboot.blog.entity.board.Post;
import com.springboot.blog.entity.habit.Habit;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.HabitRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private HabitRepository habitRepository;

    public PostServiceImpl(PostRepository postRepository, CommentRepository commentRepository, HabitRepository habitRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.habitRepository = habitRepository;
    }

    @Override
    public PostDto createPost(long habitId, PostDto postDto) {

        //retrieve habit by id
        Habit habit = habitRepository.findById(habitId).orElseThrow(() -> new ResourceNotFoundException("Habit", "habit id", habitId));
        System.out.println("1");
        // convert DTO to entity
        Post post = mapToEntity(postDto);
        System.out.println("2");
        //update habit to post
        post.updatePost(habit);
        System.out.println("3");
        Post newPost = postRepository.save(post);
        System.out.println("4");
        return mapToDTO(newPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        //create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        //get content for page Object
        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content = listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = PostResponse.builder()
                .content(content)
                .pageNo(posts.getNumber())
                .pageSize(posts.getSize())
                .totalElements(posts.getTotalElements())
                .totalPages(posts.getTotalPages())
                .last(posts.isLast())
                .build();

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        //view count + 1
        post.updateViewCount();
        Post updatedpost = postRepository.save(post);

        return mapToDTO(updatedpost);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        // get post by id from the database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        //title, content변경
        post.updatePostBody(postDto);

        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        // get post by id from the database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }


    // convert Entity into DTO
    private PostDto mapToDTO(Post post){
        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .view(post.getViewCount())
                .comments(CollectionUtils.emptyIfNull(post.getComments()).stream().map(comment -> commentToCommentDto(comment)).collect(Collectors.toSet()))
                .createdTime(post.getCreatedDate())
                .modifiedTime(post.getModifiedDate())
                .userName(post.getHabit().getMember().getMemberName())
                .build();

        return postDto;
    }

    private CommentDto commentToCommentDto(Comment comment){
        CommentDto commentDto = CommentDto.builder()
                .id(comment.getId())
                .name(comment.getMember().getMemberName())
                .body(comment.getBody())
                .createdTime(comment.getCreatedDate())
                .modifiedTime(comment.getModifiedDate())
                .build();

        return commentDto;
    }

    // convert DTO to entity
    private Post mapToEntity(PostDto postDto){
        Post post = Post.builder()
                .id(postDto.getId())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();

        return post;
    }
}
