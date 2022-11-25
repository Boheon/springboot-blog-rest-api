package com.module.blog.payload;


import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public enum GroupType {
        ALCOHOL("ALCOHOL"),
        TOBACCO("TOBACCO"),
        WORKOUT("WORKOUT");

    String groupType;

    GroupType(String groupType){
        this.groupType=groupType;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static GroupType getGroupType(String groupType) {
        for (GroupType group : GroupType.values()) {

            if (group.getGroupType().equals(groupType)) {

                return group;
            }
        }
        return null;
    }
}
