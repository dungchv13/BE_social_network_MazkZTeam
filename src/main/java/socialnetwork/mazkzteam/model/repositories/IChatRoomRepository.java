package socialnetwork.mazkzteam.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import socialnetwork.mazkzteam.model.entities.ChatRoom;

import java.util.Optional;

public interface IChatRoomRepository extends JpaRepository<ChatRoom, Integer> {

    @Query(value = "select * " +
            "from social_network.chat_room " +
            "where (first_user_id = ?1 and second_user_id = ?2) " +
            "or (first_user_id = ?2 and second_user_id = ?1)" , nativeQuery = true)
    Optional<ChatRoom> getChatRoomByIds(int firstUserId, int secondUserId);
}
