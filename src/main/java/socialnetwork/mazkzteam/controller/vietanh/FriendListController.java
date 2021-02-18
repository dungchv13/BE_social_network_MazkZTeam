package socialnetwork.mazkzteam.controller.vietanh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import socialnetwork.mazkzteam.model.entities.ChatRoom;
import socialnetwork.mazkzteam.model.entities.Friendship;
import socialnetwork.mazkzteam.model.entities.IFriend;
import socialnetwork.mazkzteam.model.entities.User;
import socialnetwork.mazkzteam.model.service.FriendshipService;
import socialnetwork.mazkzteam.model.service.UserService;
import socialnetwork.mazkzteam.model.service.hieu.IChatRoomService;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("friendlist/{username}")
public class FriendListController {
    @Autowired
    FriendshipService friendshipService;
    @Autowired
    UserService userService;
    @Autowired
    IChatRoomService chatRoomService;

    Response res = new Response();

    @GetMapping("getuser")
    public Response getUser(@PathVariable("username") String username){
        User user = userService.findUserByUsername(username);
        res.data = user;
        res.status = res.SUCCESS;
        res.message = "Success";
        return res;
    }

    @GetMapping("friendnotrequest")
    public Response FriendNotRequestList(@PathVariable("username") String username){
        User user = userService.findUserByUsername(username);
        List<IFriend> friendNotRequest = friendshipService.friendNotRequest(user.getId());
        res.data = friendNotRequest;
        res.status = res.SUCCESS;
        res.message = "Success";
        return res;
    }

    @GetMapping
    public Response FriendList(@PathVariable("username") String username) {
        User user = userService.findUserByUsername(username);
        List<IFriend> userReceiver = friendshipService.getListFriend(user.getId());
        res.data = userReceiver;
        res.status = res.SUCCESS;
        res.message = "Success";
        return res;
    }

    @GetMapping("friendreceiver")
    public Response FriendReceiverList(@PathVariable("username") String username) {
        User user = userService.findUserByUsername(username);
        List<IFriend> userReceiver = friendshipService.receiverList(user.getId());
        res.data = userReceiver;
        res.status = res.SUCCESS;
        res.message = "Success";
        return res;
    }

    @DeleteMapping("/delete")
    public void deleteFriend(@RequestParam("senderId") Integer idSender, @RequestParam("receiverId") Integer idReceiver){
        friendshipService.deleteFriend(idReceiver,idSender);
    }

    @GetMapping("/accept")
    public void acceptFriend(@RequestParam("senderId") Integer idSender, @RequestParam("receiverId")Integer idReceiver){
        if (!chatRoomService.getChatRoomByIds(idSender, idReceiver).isPresent()) {
           ChatRoom chatRoom = new ChatRoom();
           User firstUser = userService.findById(idSender);
           User secondUser = userService.findById(idReceiver);
           chatRoom.setName("/message/" + idSender + "/" + idReceiver);
           chatRoom.setFirst_user_id(idSender);
           chatRoom.setFirstUser(firstUser);
           chatRoom.setSecondUser(secondUser);
           chatRoom.setSecond_user_id(idReceiver);
           chatRoomService.save(chatRoom);
        }
        friendshipService.acceptFriend(idSender,idReceiver);
    }

    @GetMapping("/addfriend")
    public void addFriend(@RequestParam("senderId") Integer idSender, @RequestParam("receiverId")Integer idReceiver){
        friendshipService.addFriend(idSender,idReceiver);
    }

    @GetMapping("cancel")
    public void cancelFriendRequest(@RequestParam("senderId") Integer idSender, @RequestParam("receiverId")Integer idReceiver){
        friendshipService.cancelFriendRequest(idSender,idReceiver);
    }

    @GetMapping("/sendedrequest")
    public Response senderFriendRequest(@PathVariable("username") String username ){
        User user = userService.findUserByUsername(username);
        List<IFriend> senderFriendRequest = friendshipService.senderFriendRequestList(user.getId());
        res.data = senderFriendRequest;
        res.status = res.SUCCESS;
        res.message = "Success";
        return res;
    }

}
