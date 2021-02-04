package socialnetwork.mazkzteam.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import socialnetwork.mazkzteam.model.entities.Friendship;
import socialnetwork.mazkzteam.model.entities.IFriend;
import socialnetwork.mazkzteam.model.entities.User;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship,Integer> {

    @Query(value = "SELECT social_network.user.username,social_network.user.avatar " +
            "FROM user " +
            "INNER JOIN friendship " +
            "ON user.id = friendship.user_receiver_id where friendship.status = false and friendship.user_sender_id =?1",nativeQuery = true)
    List<IFriend> receiverList(Integer id);

    @Query(value = "SELECT social_network.user.username,social_network.user.avatar " +
            "FROM user " +
            "INNER JOIN friendship " +
            "ON user.id = friendship.user_receiver_id where friendship.status = true and friendship.user_sender_id =?1",nativeQuery = true)
    List<IFriend> getListFriend(Integer id);

}
