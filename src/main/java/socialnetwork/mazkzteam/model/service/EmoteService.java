package socialnetwork.mazkzteam.model.service;

import socialnetwork.mazkzteam.model.entities.Emote;

import java.util.List;

public interface EmoteService extends CommonService<Emote>{
    boolean disLiked(int postId, int userId);

    boolean dislikedComment(int cm_id, int user_id);
}
