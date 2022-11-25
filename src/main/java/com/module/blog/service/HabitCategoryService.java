package com.module.blog.service;

import com.module.blog.payload.HabitCategoryDTO;
import com.module.blog.payload.HabitCategoryResponse;

public interface HabitCategoryService {
    HabitCategoryDTO createHabitCategory(HabitCategoryDTO habitCategoryDTO);

    HabitCategoryResponse getAllHabitCategories(int pageNo,int pageSize, String sortBy, String sortDir);

    HabitCategoryDTO getHabitCategoryByID(long id);

    HabitCategoryDTO updateHabitCategory(HabitCategoryDTO habitCategoryDTO, long id);

    void deleteHabitCategoryByID(long id);
}
