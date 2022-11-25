package com.module.blog.payload;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class HabitCategoryDTO {
    private Long id;

    @NotEmpty
    private String name;
}
