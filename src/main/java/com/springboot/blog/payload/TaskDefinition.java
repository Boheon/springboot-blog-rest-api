package com.springboot.blog.payload;

import lombok.Data;

@Data
public class TaskDefinition {//동적 스케줄러 시간표

    private String cronExpression; //크롬식
    private String actionType; //
    private String data; //

}
