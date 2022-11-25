package com.module.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetailDto {

    String email;
    String name;
}
