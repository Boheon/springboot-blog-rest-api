package com.springboot.blog.entity.chat;

import com.springboot.blog.entity.common.BaseTimeEntity;
import com.springboot.blog.entity.band.Band;
import com.springboot.blog.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Chat extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Long chatID;

    @Column(name = "chat_content", nullable = false)
    private String chatContent;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "groups_id")
    private Band band;
}
