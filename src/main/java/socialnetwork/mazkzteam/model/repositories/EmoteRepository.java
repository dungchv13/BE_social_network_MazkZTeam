package socialnetwork.mazkzteam.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import socialnetwork.mazkzteam.model.entities.Emote;

public interface EmoteRepository extends JpaRepository<Emote,Integer> {
}
