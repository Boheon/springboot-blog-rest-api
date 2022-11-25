package com.module.blog.payload;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Data
public class RoomDto {
    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public static RoomDto create(String name) {

        RoomDto room = new RoomDto();
        room.name = name;
        return room;
    }
}
