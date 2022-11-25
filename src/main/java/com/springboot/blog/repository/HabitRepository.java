package com.springboot.blog.repository;

import com.springboot.blog.entity.habit.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HabitRepository extends JpaRepository<Habit, Long> {
    List<Habit> findByHabitCategoryId(long habitCategoryId);

    List<Habit> findByMemberId(long memberId);

}
