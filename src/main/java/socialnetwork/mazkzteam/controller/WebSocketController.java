package socialnetwork.mazkzteam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import socialnetwork.mazkzteam.model.entities.ChatMessage;
import socialnetwork.mazkzteam.model.entities.ChatRoom;
import socialnetwork.mazkzteam.model.entities.Friendship;
import socialnetwork.mazkzteam.model.service.FriendshipService;
import socialnetwork.mazkzteam.model.service.hieu.IChatRoomService;
import socialnetwork.mazkzteam.model.service.hieu.IMessageService;
import socialnetwork.mazkzteam.model.service.hieu.IUserService;


@CrossOrigin(origins = "*")
@RestController
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    IUserService userService;

    @Autowired
    IMessageService messageService;
    @Autowired
    IChatRoomService chatRoomService;
    @Autowired
    FriendshipService friendshipService;


    @MessageMapping("/send/message/{chatRoomId}")
    public ChatMessage sendMessageTo(@Payload ChatMessage chatMessage, @DestinationVariable("chatRoomId") int chatRoomId){
        System.out.println(chatMessage);
        ChatRoom chatRoom;
        if (chatRoomService.findById(chatRoomId).isPresent()) {
            chatRoom = chatRoomService.findById(chatRoomId).get();
            this.template.convertAndSend(chatRoom.getName(), chatMessage);
            messageService.save(chatMessage);
        }
        return chatMessage;
    }

    @MessageMapping("/friends/add/{idSender}/{idReceiver}")
    public void addFriend(@DestinationVariable("idSender") int idSender, @DestinationVariable("idReceiver") int idReceiver){
        friendshipService.addFriend(idSender,idReceiver);
        this.template.convertAndSend("/friends","1");
    }
}
