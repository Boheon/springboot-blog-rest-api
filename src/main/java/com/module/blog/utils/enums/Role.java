package com.module.blog.utils.enums;

public enum Role {
    ROLE_USER("USER"),
    ROLE_NOT_VERIFIED("NOT_VERIFIED"),
    ROLE_ADMIN("ADMIN"),
    ROLE_GROUP_DENIED("GROUP_DENIED"),
    ROLE_GROUP_PENDING("GROUP_PENDING"),
    ROLE_GROUP_USER("GROUP_USER"),
    ROLE_GROUP_ADMIN("GROUP_ADMIN");

    String role;

    Role(String role) {
        this.role = role;
    }

    public String withRolePrefix(){
        return "ROLE_"+role;
    }
}
