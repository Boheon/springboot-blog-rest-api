package com.springboot.blog.payload;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;


@Data
@Builder

public class HabitDTO {
    private Long id;

    @NotEmpty
    private String name;

    @NotNull
    private Long amount;

    @NotNull
    private Long period;//단위 밀리초

    private Long count;

    private Timestamp date;

    private Boolean check;

    private String location;

}
