package com.springboot.blog.repository;

import com.springboot.blog.entity.habit.HabitCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitCategoryRepository extends JpaRepository<HabitCategory, Long> {

}
