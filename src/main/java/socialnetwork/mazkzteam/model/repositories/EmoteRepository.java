package socialnetwork.mazkzteam.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import socialnetwork.mazkzteam.model.entities.Emote;

import java.util.List;

public interface EmoteRepository extends JpaRepository<Emote, Integer> {
    @Query(value = "select * from social_network.emote where ?1 in (select post_id from emote where user_id = ?2)", nativeQuery = true)
    List<Emote> isLiked(int postId, int userId);

    @Transactional
    void deleteEmoteByPost_idAndUser_id(int postId, int userId);
}
