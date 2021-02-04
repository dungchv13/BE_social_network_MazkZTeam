package socialnetwork.mazkzteam.controller.vietanh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import socialnetwork.mazkzteam.model.entities.Friendship;
import socialnetwork.mazkzteam.model.entities.IFriend;
import socialnetwork.mazkzteam.model.entities.User;
import socialnetwork.mazkzteam.model.service.FriendshipService;
import socialnetwork.mazkzteam.model.service.UserService;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("friendlist/{username}")
public class FriendListController {
    @Autowired
    FriendshipService friendshipService;
    @Autowired
    UserService userService;

    Response res = new Response();

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

}
