package com.springboot.blog.entity.member;

import com.springboot.blog.entity.board.Comment;
import com.springboot.blog.entity.chat.Chat;
import com.springboot.blog.entity.common.BaseTimeEntity;
import com.springboot.blog.entity.habit.Habit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Column(name = "member_email", nullable = false)
    private String memberEmail;

    @Column(name = "member_password", nullable = false)
    private String memberPassword;

    @Column(name = "member_verification", nullable = false)
    private Boolean memberVerification;//토큰인증

    @Column(name = "member_name", nullable = false)
    private String memberName;

    @Column(name = "member_nickname", nullable =false)
    private String memberNickname;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<BandMember> bandMembers = new LinkedList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Habit> habits = new LinkedList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Comment> comments = new LinkedList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Chat> chats = new LinkedList<>();

}
