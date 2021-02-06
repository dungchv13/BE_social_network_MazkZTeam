package socialnetwork.mazkzteam.model.service;

import socialnetwork.mazkzteam.model.entities.Emote;

public interface EmoteService extends CommonService<Emote>{
    boolean disLiked(int postId, int userId);
    boolean dislikedComment(int cm_id, int user_id);
    boolean dislikedPost(int post_id, int user_id);

}
