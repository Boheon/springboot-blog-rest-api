package com.module.group;


import com.module.user.Role;
import com.module.user.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "user_in_group")
public class UserInGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    //there should not be the same name in the group
    @NotNull
    String nickName;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    Role role;

    @Column(name = "warn_count", columnDefinition = "int default 0")
    int warnCount;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private Group group;

    @Override
    public String toString(){
        return "the group name is: " + group.getName() + " user nick name is : " + this.getNickName() +" Role is: "+ this.getRole();
    }

}
