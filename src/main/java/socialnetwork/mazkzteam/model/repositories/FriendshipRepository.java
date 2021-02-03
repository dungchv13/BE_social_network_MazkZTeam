package socialnetwork.mazkzteam.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import socialnetwork.mazkzteam.model.entities.Friendship;

public interface FriendshipRepository extends JpaRepository<Friendship,Integer> {
}
