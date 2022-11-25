package com.module.blog.repository;

import com.module.blog.entity.habit.HabitCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitCategoryRepository extends JpaRepository<HabitCategory, Long> {

}
