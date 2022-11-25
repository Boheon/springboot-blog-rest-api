package com.module.blog.payload;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

import java.sql.Timestamp;

@Data
@Builder
public class CommentDto {

    private long id;

    private String name;

    //comment body should not be empty or null
    @NotEmpty
    private String body;

    private Timestamp createdTime;

    private Timestamp modifiedTime;
}
