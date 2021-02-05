package socialnetwork.mazkzteam.model.service;

import socialnetwork.mazkzteam.model.entities.Emote;

import java.util.List;

public interface EmoteService extends CommonService<Emote>{
    List<Emote> isLiked(int postId, int userId);
    void disLiked(int postId, int userId);
}
