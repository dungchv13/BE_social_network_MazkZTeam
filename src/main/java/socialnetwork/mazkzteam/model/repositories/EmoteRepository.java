package socialnetwork.mazkzteam.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import socialnetwork.mazkzteam.model.entities.Emote;

public interface EmoteRepository extends JpaRepository<Emote,Integer> {
    @Transactional
    void deleteEmoteByPost_idAndUser_id(int postId, int userId);

    @Transactional
    void deleteEmoteByComment_idAndUser_id(int commentId, int userId);
}
