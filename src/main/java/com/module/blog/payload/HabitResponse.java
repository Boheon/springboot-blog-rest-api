package com.module.blog.payload;

import com.module.blog.entity.habit.Habit;

import java.util.List;

public class HabitResponse {
    private List<Habit> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
