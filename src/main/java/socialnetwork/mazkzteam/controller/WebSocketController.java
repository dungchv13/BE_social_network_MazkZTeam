package socialnetwork.mazkzteam.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import socialnetwork.mazkzteam.model.entities.ChatMessage;
import socialnetwork.mazkzteam.model.entities.ChatRoom;
import socialnetwork.mazkzteam.model.entities.Friendship;
import socialnetwork.mazkzteam.model.service.FriendshipService;
import socialnetwork.mazkzteam.model.entities.Notification;
import socialnetwork.mazkzteam.model.entities.User;
import socialnetwork.mazkzteam.model.service.NotificationService;
import socialnetwork.mazkzteam.model.entities.Club;
import socialnetwork.mazkzteam.model.entities.User;
import socialnetwork.mazkzteam.model.service.ClubService;
import socialnetwork.mazkzteam.model.service.UserService;
import socialnetwork.mazkzteam.model.service.hieu.IChatRoomService;
import socialnetwork.mazkzteam.model.service.hieu.IMessageService;
import socialnetwork.mazkzteam.model.service.hieu.IUserService;

import java.sql.Timestamp;
import java.time.LocalDateTime;


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

    @Autowired
    private UserService us;

    @Autowired
    private ClubService clubService;


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

    @MessageMapping("/friends/add/{idSender}/{idReceiver}")
    public void addFriend(@DestinationVariable("idSender") int idSender, @DestinationVariable("idReceiver") int idReceiver){
        friendshipService.addFriend(idSender,idReceiver);
        this.template.convertAndSend("/friends","1");
    }

    @MessageMapping("/friends/cancel/{idSender}/{idReceiver}")
    public void cancelRequest(@DestinationVariable("idSender") int idSender, @DestinationVariable("idReceiver") int idReceiver){
        friendshipService.cancelFriendRequest(idSender,idReceiver);
        this.template.convertAndSend("/friends","1");
    }

    @MessageMapping("/friends/accept/{idSender}/{idReceiver}")
    public void acceptRequest(@DestinationVariable("idSender") int idSender, @DestinationVariable("idReceiver") int idReceiver){
        if (!chatRoomService.getChatRoomByIds(idSender, idReceiver).isPresent()) {
            ChatRoom chatRoom = new ChatRoom();
            User firstUser = userService.findById(idSender).get();
            User secondUser = userService.findById(idReceiver).get();
            chatRoom.setName("/message/" + idSender + "/" + idReceiver);
            chatRoom.setFirst_user_id(idSender);
            chatRoom.setFirstUser(firstUser);
            chatRoom.setSecondUser(secondUser);
            chatRoom.setSecond_user_id(idReceiver);
            chatRoomService.save(chatRoom);
        }
        friendshipService.acceptFriend(idSender,idReceiver);
        this.template.convertAndSend("/friends","1");
    }

    @MessageMapping("/friends/unfriend/{idSender}/{idReceiver}")
    public void unFriend(@DestinationVariable("idSender") int idSender, @DestinationVariable("idReceiver") int idReceiver){
        friendshipService.deleteFriend(idSender,idReceiver);
        this.template.convertAndSend("/friends","1");
    }

    @MessageMapping("/clubsocket/{username}/creatgroup")
    public Club createGroup(@Payload Club club, @DestinationVariable("username") String username){
        User user = us.findUserByUsername(username);
        club.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        club.setFounder_id(user.getId());
        club.getMembers().add(user);
        club = clubService.save(club);
        this.template.convertAndSend("/clubsocket",new ClubSocket(false,club));
        return club;

    }

    @MessageMapping("/clubsocket/deleteclub/{club_id}")
    public boolean deleteClub(@DestinationVariable("club_id")int club_id){
        Club club = clubService.findById(club_id);

        for (User user :club.getMembers()) {
            clubService.leaveClub(user.getId(),club_id);
        }
        for (User user :club.getUserReqToJoi()) {
            clubService.cancelJoinReq(user.getId(),club_id);
        }

        boolean isDelete = clubService.deleteById(club_id);

        this.template.convertAndSend("/clubsocket",new ClubSocket(true,club));

        return isDelete;
    }

}
class ClubSocket{

    @JsonProperty
    private boolean isDelete;

    private Club club;

    public ClubSocket() {
    }

    public ClubSocket(boolean isDelete, Club club) {
        this.isDelete = isDelete;
        this.club = club;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}
