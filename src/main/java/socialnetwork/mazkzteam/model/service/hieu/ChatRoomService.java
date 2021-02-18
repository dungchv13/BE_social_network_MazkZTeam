package socialnetwork.mazkzteam.model.service.hieu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import socialnetwork.mazkzteam.model.entities.ChatRoom;
import socialnetwork.mazkzteam.model.repositories.IChatRoomRepository;

import java.util.Optional;

@Service
public class ChatRoomService implements IChatRoomService {
    @Autowired
    private IChatRoomRepository chatRoomRepository;
    @Override
    public Iterable<ChatRoom> findAll() {
        return chatRoomRepository.findAll();
    }

    @Override
    public Optional<ChatRoom> findById(int id) {
        return chatRoomRepository.findById(id);
    }

    @Override
    public ChatRoom save(ChatRoom chatRoom) {
        return chatRoomRepository.save(chatRoom);
    }

    @Override
    public void remove(int id) {
        chatRoomRepository.deleteById(id);
    }

    @Override
    public Optional<ChatRoom> getChatRoomByIds(int firstUserId, int secondUserId) {
        return chatRoomRepository.getChatRoomByIds(firstUserId, secondUserId);
    }
}
