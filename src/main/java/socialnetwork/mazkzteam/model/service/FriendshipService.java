package socialnetwork.mazkzteam.model.service;

import socialnetwork.mazkzteam.model.entities.Friendship;
import socialnetwork.mazkzteam.model.entities.User;

import java.util.List;

public interface FriendshipService extends CommonService<Friendship>{
    List<Friendship> receiverList(Integer id);

}
