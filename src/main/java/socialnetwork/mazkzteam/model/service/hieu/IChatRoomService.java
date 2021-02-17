package socialnetwork.mazkzteam.model.service.hieu;

import socialnetwork.mazkzteam.model.entities.ChatRoom;

import java.util.Optional;

public interface IChatRoomService extends IGeneralService<ChatRoom> {
    Optional<ChatRoom> getChatRoomByIds(int firstUserId, int secondUserId);
}
