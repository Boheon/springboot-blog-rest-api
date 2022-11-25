package com.module.blog.repository;

import com.module.group.dto.SortByGroupUserNumberDto;
import com.module.user.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInGroupRepository extends JpaRepository<UserInGroup, Integer> {

    @Query("SELECT COUNT(u) FROM UserInGroup u WHERE u.group IN(:group_id) GROUP BY u.group")
    public List<Integer> countUserInGroupsByGroupIn(@Param("group_id") List<Group> group_id);

    @Query(value = "SELECT new com.module.group.dto.SortByGroupUserNumberDto(COUNT(u),u.group) FROM UserInGroup u WHERE u.group IN(:group_id) GROUP BY u.group ORDER BY COUNT(u) DESC")
    public List<SortByGroupUserNumberDto> countUserWithGroupInGroupsByGroup(@Param("group_id") List<Group> group_id, Pageable pageable);

    @Query("SELECT u FROM UserInGroup u WHERE u.group = :group_id AND u.nickName = :nick_name")
    public UserInGroup findUserInGroupByNickNameAndGroup(@Param("group_id") Group group_id, @Param("nick_name") String nick_name);

    @Query("SELECT u FROM UserInGroup u WHERE u.group = :group_id")
    public List<UserInGroup> findUserInGroupsByGroup(@Param("group_id") Group group_id);

    @Query("SELECT u FROM UserInGroup u WHERE u.nickName = :nick_name")
    public UserInGroup findUserInGroupByNickName(@Param("nick_name") String nick_name);

    @Query("SELECT u.nickName FROM UserInGroup  u WHERE u.group IN(:group_id) AND u.role = :role GROUP BY u.nickName")
    public List<String> getNickNameByGroupAndAdmin(@Param("group_id")List<Group> group_id,@Param("role") Role role);
}
