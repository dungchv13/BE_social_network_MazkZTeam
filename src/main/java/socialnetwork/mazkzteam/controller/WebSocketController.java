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
import socialnetwork.mazkzteam.model.entities.Notification;
import socialnetwork.mazkzteam.model.entities.User;
import socialnetwork.mazkzteam.model.service.NotificationService;
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

    @Autowired
    NotificationService notificationService;


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
    @MessageMapping("/notification")
    public Notification sendNotification(@Payload Notification notification){
        System.out.println(notification);
        User sender = userService.findById(notification.getUser_sender_id()).get();
        User receiver = userService.findById(notification.getUser_receiver_id()).get();
        notification.setStatus(false);
        notification.setUserSender(sender);
        notification.setUserReceiver(receiver);
        notificationService.save(notification);
        this.template.convertAndSend("/notification", notification);
        return notification;
    }

}
