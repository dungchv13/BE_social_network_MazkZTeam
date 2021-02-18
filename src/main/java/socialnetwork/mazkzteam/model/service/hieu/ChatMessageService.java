package socialnetwork.mazkzteam.model.service.hieu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import socialnetwork.mazkzteam.model.entities.ChatMessage;
import socialnetwork.mazkzteam.model.repositories.IMessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ChatMessageService implements IMessageService {
    @Autowired
    private IMessageRepository messageRepository;

    @Override
    public List<ChatMessage> getChatMessageByChatRoom(int chatRoomId) {
        return messageRepository.getChatMessageByChatRoom(chatRoomId);
    }


    @Override
    public Iterable<ChatMessage> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Optional<ChatMessage> findById(int id) {
        return messageRepository.findById(id);
    }

    @Override
    public ChatMessage save(ChatMessage chatMessage) {
        return messageRepository.save(chatMessage);
    }

    @Override
    public void remove(int id) {
        messageRepository.deleteById(id);
    }
}
