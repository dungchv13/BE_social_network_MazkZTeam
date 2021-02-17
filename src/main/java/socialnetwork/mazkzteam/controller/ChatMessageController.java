package socialnetwork.mazkzteam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import socialnetwork.mazkzteam.model.entities.ChatMessage;
import socialnetwork.mazkzteam.model.service.hieu.ChatMessageService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/message")
public class ChatMessageController {
    @Autowired
    private ChatMessageService chatMessageService;

    @GetMapping("/{chat_room_id}")
    public List<ChatMessage> getChatMessages(@PathVariable int chat_room_id) {
        try {
            return chatMessageService.getChatMessageByChatRoom(chat_room_id);
        } catch (Exception e) {
            return null;
        }
    }
}
