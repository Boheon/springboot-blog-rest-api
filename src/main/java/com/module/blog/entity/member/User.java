package com.module.blog.entity.member;

import com.module.board.Article;
import com.module.group.UserInGroup;
import com.module.habit.HabitStatics;
import com.module.mail.EmailToken;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "member")
public class User implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(length = 128, nullable = false)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(name = "name", length = 45, nullable = false)
    private String name;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name ="warning_count")
    private int warningCount;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<UserInGroup> userGroup = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<HabitStatics> habitStaticsList;


    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Article> articleList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<EmailToken> emailTokenList;


    @Override
    public String toString(){

        return " email: " + this.getEmail() +
                " name: " + this.getName() +
                " role: " + this.getRole()
                +" ";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(this.role.withRolePrefix()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



}
