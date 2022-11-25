package com.springboot.blog.payload;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Builder
public class PostDto {
    private long id;

    //title should not be null or empty
    //title should have at least 2 characters
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    // content should not empty or null
    @NotEmpty
    private String content;

    private String userName;

    private long view;

    private Set<CommentDto> comments;

    private Timestamp createdTime;

    private Timestamp modifiedTime;
}
