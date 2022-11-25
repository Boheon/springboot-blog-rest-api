package com.module.blog.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.module.group.GroupType;
import com.module.validator.GroupNameDuplicationCheck;
import com.module.validator.NickNameDuplicationCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateGroupDto {
    @GroupNameDuplicationCheck
    @JsonProperty("groupName")
    private String groupName;

    @NickNameDuplicationCheck
    @JsonProperty("nickName")
    private String nickName;

    @JsonProperty("groupType")
    private GroupType groupType;


    @Override
    public String toString(){
        return "groupName: "+ groupName  +
                "group type: "+ groupType +
                "nick name: " + nickName;
    }

}
