package socialnetwork.mazkzteam.controller.vietanh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import socialnetwork.mazkzteam.model.entities.Friendship;
import socialnetwork.mazkzteam.model.entities.User;
import socialnetwork.mazkzteam.model.service.FriendshipService;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("friendlist")
public class FriendListController {
    @Autowired
    FriendshipService friendshipService;
    Response res = new Response();

    @GetMapping
    public Response FriendReceiverList(@RequestParam Integer id){
        List<Friendship> userReceiver = friendshipService.receiverList(id);
        res.data = userReceiver;
        res.status = res.SUCCESS;
        res.message = "Success";
        return res;
    }
}
