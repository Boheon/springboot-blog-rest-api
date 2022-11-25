package com.springboot.blog.payload;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Builder
public class HabitCategoryDTO {
    private Long id;

    @NotEmpty
    private String name;
}
