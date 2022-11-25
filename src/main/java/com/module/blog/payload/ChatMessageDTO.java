package com.module.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@AllArgsConstructor
public class ChatMessageDTO {

    private String roomId;
    private String writer;
    private String message;

}

