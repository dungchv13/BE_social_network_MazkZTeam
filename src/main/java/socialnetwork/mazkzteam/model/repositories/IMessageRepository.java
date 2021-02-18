package socialnetwork.mazkzteam.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import socialnetwork.mazkzteam.model.entities.ChatMessage;

import java.util.List;

public interface IMessageRepository extends JpaRepository<ChatMessage, Integer> {
    @Query(value = "select * " +
            "from social_network.chat_message " +
            "where chat_room_id =?1", nativeQuery = true)
    List<ChatMessage> getChatMessageByChatRoom(int chatRoomId);

}
