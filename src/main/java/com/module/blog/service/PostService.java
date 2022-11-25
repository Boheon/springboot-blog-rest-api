package com.module.blog.service;

import com.module.blog.payload.PostDto;
import com.module.blog.payload.PostResponse;


public interface PostService {
    PostDto createPost(long habitId, PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePostById(long id);


}
