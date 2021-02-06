package socialnetwork.mazkzteam.model.service;

import socialnetwork.mazkzteam.model.entities.Emote;

public interface EmoteService extends CommonService<Emote>{
    boolean dislikedPost(int post_id, int user_id);
    boolean dislikedComment(int comment_id, int user_id);
}
