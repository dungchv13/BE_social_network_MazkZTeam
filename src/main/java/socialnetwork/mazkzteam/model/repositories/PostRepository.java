package socialnetwork.mazkzteam.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import socialnetwork.mazkzteam.model.entities.Post;

public interface PostRepository extends JpaRepository<Post,Integer> {
}
