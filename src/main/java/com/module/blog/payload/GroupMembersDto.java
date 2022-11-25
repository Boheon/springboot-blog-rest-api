package com.module.blog.payload;

import com.module.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupMembersDto {
    String nickName;
    Role role;
}
