package socialnetwork.mazkzteam.model.service;

import org.springframework.data.jpa.repository.Query;
import socialnetwork.mazkzteam.model.entities.Friendship;
import socialnetwork.mazkzteam.model.entities.IFriend;
import socialnetwork.mazkzteam.model.entities.User;

import java.util.List;

public interface FriendshipService extends CommonService<Friendship>{
    List<IFriend> receiverList(Integer id);
    List<IFriend> getListFriend(Integer id);
    void deleteFriend(Integer idReceiver,Integer idSender);
    void acceptFriend(Integer idSender, Integer idReceiver);
    void addFriend(Integer idSender, Integer idReceiver);
    void cancelFriendRequest(Integer idSender, Integer idReceiver);


}
