package com.module.group;

import com.module.room.Room;
import lombok.*;
import reactor.util.annotation.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "the_group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @NotNull
    private String name;

    //input the type of habit
    @NotNull
    @Enumerated(EnumType.STRING)
    private GroupType groupType;
    //admin should be user email
    @NotNull
    private String admin;

    @OneToMany(mappedBy = "group", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<UserInGroup> userInGroup;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn
    private Room room;

    @Override
    public String toString(){
    return "Group name : " + this.name +"'\n" + "Type: "+ this.groupType.getGroupType() + "" + "\n";
}
}
