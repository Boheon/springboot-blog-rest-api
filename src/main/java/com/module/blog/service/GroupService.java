package com.module.blog.service;

import com.module.group.dto.SortByGroupUserNumberDto;
import com.module.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface GroupService extends UserDetailsService {
    List<Group> sortByTypeName(GroupType groupType, Integer page);
    List<SortByGroupUserNumberDto> sortByUserNumberInGroup(List<Group> groupList, Integer page);
    List<Integer> getUserNumberInGroup(List<Group> groups);
    Group getGroupByAdminAndGroupName(String adminNickName , String groupName);
    Group getGroupByGroupName(String groupName);
    Group updateGroup(Group group);
    void createGroup(String groupName, String adminEmail, String adminNickName, GroupType groupType);
    void deleteGroup(Group group);
    String getAdminEmailByAdminNickName(String adminEmail);
    UserInGroup applyGroup(String admin , String groupName, User user, String nickName);
    UserInGroup getUserInGroupByUserNickNameAndGroup(String nickName, Group group);
    UserInGroup getUserInGroupByNickName(String nickName);
    UserInGroup consentOrDenyUser(UserInGroup userInGroup);
    UserInGroup updateUserInGroup(UserInGroup userInGroup);
    void takeOverAdmin(String groupName, String userNickName);
    List<UserInGroup> getMembersInGroup(String groupName);
    List<String> getAdminNickNameByGroupList(List<Group> groupList);
    void kickUserInGroupOrWithdrawal(UserInGroup userInGroup);
    boolean groupNameIsDuplicated(String groupName);
    boolean NickNameIsDuplicated(String nickName);
}
