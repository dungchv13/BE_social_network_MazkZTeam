package socialnetwork.mazkzteam.model.service;

import socialnetwork.mazkzteam.model.entities.Emote;

public interface EmoteService extends CommonService<Emote>{
    boolean disliked(int post_id, int user_id);
}
