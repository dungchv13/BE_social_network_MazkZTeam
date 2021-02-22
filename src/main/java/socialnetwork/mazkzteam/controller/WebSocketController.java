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
import socialnetwork.mazkzteam.model.entities.*;
import socialnetwork.mazkzteam.model.service.*;
import socialnetwork.mazkzteam.model.entities.User;
import socialnetwork.mazkzteam.model.service.hieu.IChatRoomService;
import socialnetwork.mazkzteam.model.service.hieu.IMessageService;
import socialnetwork.mazkzteam.model.service.hieu.IUserService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


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

    @Autowired
    private PostService postService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private EmoteService emoteService;

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

        postService.deleteAllByClub_id(club_id);

        boolean isDelete = clubService.deleteById(club_id);

        this.template.convertAndSend("/clubsocket",new ClubSocket(true,club));

        return isDelete;
    }

    @MessageMapping("/newsfeed/{username}/createPost")
    public void createPost(@DestinationVariable("username") String username, @Payload Post post){
        User user = us.findUserByUsername(username);
        post.setUser_id(user.getId());
        post.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        if(post.getClub_id() == 0){
            post.setClub_id(9999);
        }
        Post post1 = postService.save(post);

        List<Photo> photoList = post.getPhotoList();
        for (Photo photo : photoList) {
            photo.setPost_id(post.getId());
        }
        photoService.saveAllPhoto(photoList);
        post1.setUser(user);
        this.template.convertAndSend("/newsfeed",post1);
    }

    @MessageMapping("/newsfeed/{username}/editPost")
    public void editPost(@DestinationVariable("username") String username, @Payload Post post){
        Post post1 = postService.findById(post.getId());
        post1.setModifiedAt(Timestamp.valueOf(LocalDateTime.now()));
        post1.setContent(post.getContent());
        post1.setProtective(post.getProtective());

        photoService.deleteAllPhoto(post1.getId());

        List<Photo> newPhotoList = post.getPhotoList();
        for (Photo photo : newPhotoList) {
            photo.setPost_id(post.getId());
        }
        photoService.saveAllPhoto(newPhotoList);
        postService.save(post1);
        this.template.convertAndSend("/newsfeed","succes");
    }
    @MessageMapping("/newsfeed/deletePost/{id}")
    public void deletePost(@DestinationVariable("id") int id){
        postService.deleteById(id);
        this.template.convertAndSend("/newsfeed","success");
    }

    @MessageMapping("/newsfeed/createComment")
    public void createComment(@Payload Comment comment){
        User user = us.findById(comment.getUser_id());
        comment.setUser_id(user.getId());
        comment.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        Comment newComment = commentService.save(comment);
        newComment.setUser(user);
        this.template.convertAndSend("/newsfeed","success");
    }

    @MessageMapping("/newsfeed/updateComment")
    public void updateComment(@Payload Comment comment){
        Comment comment1 = commentService.findById(comment.getId());
        comment1.setContent(comment.getContent());
        commentService.save(comment1);
        this.template.convertAndSend("/newsfeed","success");
    }

    @MessageMapping("/newsfeed/deleteComment/{id}")
    public void updateComment(@DestinationVariable("id") int id){
        commentService.deleteById(id);
        this.template.convertAndSend("/newsfeed","success");
    }

    @MessageMapping("/newsfeed/createEmote")
    public void createEmote(@Payload Emote emote){
        Emote emote1 = emoteService.save(emote);
        this.template.convertAndSend("/newsfeed","success");
    }

    @MessageMapping("/newsfeed/deleteEmotePost/{username}/{id}")
    public void deleteEmotePost(@DestinationVariable("id") int id, @DestinationVariable("username") String username){
        User user = us.findUserByUsername(username);
        emoteService.dislikedPost(id, user.getId());
        this.template.convertAndSend("/newsfeed","success");
    }

    @MessageMapping("/newsfeed/deleteEmoteComment/{username}/{id}")
    public void deleteEmoteComment(@DestinationVariable("id") int id, @DestinationVariable("username") String username){
        User user = us.findUserByUsername(username);
        emoteService.dislikedComment(id, user.getId());
        this.template.convertAndSend("/newsfeed","success");
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
