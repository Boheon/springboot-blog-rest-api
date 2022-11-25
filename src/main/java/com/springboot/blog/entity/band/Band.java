package com.springboot.blog.entity.band;

import com.springboot.blog.entity.chat.Chat;
import com.springboot.blog.entity.common.BaseTimeEntity;
import com.springboot.blog.entity.member.BandMember;
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
public class Band extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "band_id", nullable = false)
    private Long bandID;

    @Lob
    @Column(name = "band_description", nullable = false)
    private String bandDescription;

    @Column(name = "band_name", nullable = false)
    private String bandName;

    @OneToMany(mappedBy = "band", fetch = FetchType.LAZY)
    private List<BandMember> bandMembers = new LinkedList<>();

    @OneToMany(mappedBy = "band", fetch = FetchType.LAZY)
    private List<Chat> chats = new LinkedList<>();

}
