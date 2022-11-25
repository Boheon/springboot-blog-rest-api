package com.springboot.blog.service;

import com.springboot.blog.payload.HabitCategoryDTO;
import com.springboot.blog.payload.HabitCategoryResponse;

public interface HabitCategoryService {
    HabitCategoryDTO createHabitCategory(HabitCategoryDTO habitCategoryDTO);

    HabitCategoryResponse getAllHabitCategories(int pageNo,int pageSize, String sortBy, String sortDir);

    HabitCategoryDTO getHabitCategoryByID(long id);

    HabitCategoryDTO updateHabitCategory(HabitCategoryDTO habitCategoryDTO, long id);

    void deleteHabitCategoryByID(long id);
}
