package socialnetwork.mazkzteam.model.service.hieu;

import socialnetwork.mazkzteam.model.entities.ChatMessage;

import java.util.List;

public interface IMessageService extends IGeneralService<ChatMessage>{
    List<ChatMessage> getChatMessageByChatRoom(int chatRoomId);
}
