package com.module.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@RestController("/api/group/user")
public class ChatController {

    private final SimpMessagingTemplate template;


    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDTO message) {
        message.setMessage(message.getWriter() + "is now at chat-room.");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDTO message) {
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}
