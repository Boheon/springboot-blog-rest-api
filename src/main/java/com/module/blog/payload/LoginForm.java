package com.module.blog.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginForm {

    @JsonProperty
    @NotNull
    private String email;
    @JsonProperty
    @NotNull
    private String password;
}
