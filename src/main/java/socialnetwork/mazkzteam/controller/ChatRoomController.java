package socialnetwork.mazkzteam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import socialnetwork.mazkzteam.model.entities.ChatRoom;
import socialnetwork.mazkzteam.model.service.hieu.ChatRoomService;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/chatroom")
public class ChatRoomController {
    @Autowired
    private ChatRoomService chatRoomService;

    @GetMapping("/{first_user_id}/{second_user_id}")
    public ChatRoom getRoomByIds(@PathVariable("first_user_id") int first_user_id, @PathVariable("second_user_id") int second_user_id) {
            Optional<ChatRoom> chatRoom = chatRoomService.getChatRoomByIds(first_user_id, second_user_id);
        if (chatRoom.isPresent()) {
            return chatRoom.get();
        }
        return null;
    }
}
