package com.springboot.blog.entity.member;

import com.springboot.blog.entity.band.Band;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BandMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "band_member_id", nullable = false)
    private Long id;

    @Column(name = "member_approved", nullable = false)
    private Boolean memberApproved;

    @Column(name = "warning_count", nullable = false)
    private Long warningCount;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "band_id")
    private Band band;

}
