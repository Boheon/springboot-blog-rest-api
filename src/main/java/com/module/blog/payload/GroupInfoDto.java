package com.module.blog.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.module.group.GroupType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupInfoDto {


    int userNumberInGroup;
    String groupName;
    GroupType groupType;
    String adminNickName;



    @Override
    public String toString(){
        return "group name: " + groupName + "\n" +
                "group type: " + groupType + "\n" +
                "admin nick-name: " + adminNickName + "\n" +
                "user number in group: " + userNumberInGroup;
    }
}
