package com.module.blog.entity.habit;

import com.module.blog.entity.common.BaseTimeEntity;
import com.module.blog.payload.HabitCategoryDTO;
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

public class HabitCategory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "habit_category_id", nullable = false)
    private Long id;

    @Column(name = "habit_category_name", nullable = false)
    private String habitCategoryName;

    @OneToMany(mappedBy = "habitCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Habit> habits = new LinkedList<>();

    public void updateHabitCategory(HabitCategoryDTO habitCategoryDTO){
        this.habitCategoryName = habitCategoryDTO.getName();
    }

}
