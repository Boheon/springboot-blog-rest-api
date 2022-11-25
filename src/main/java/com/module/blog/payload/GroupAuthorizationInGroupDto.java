package com.module.blog.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.module.json.UserAuthorizationJsonHandler;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@JsonDeserialize(using = UserAuthorizationJsonHandler.class)
public class GroupAuthorizationInGroupDto {

    @JsonProperty("groupName")
    private String groupName;

    @JsonProperty("myNickName")
    private String myNickName;


}