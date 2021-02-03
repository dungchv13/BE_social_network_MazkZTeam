package socialnetwork.mazkzteam.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import socialnetwork.mazkzteam.model.entities.Post;
import socialnetwork.mazkzteam.model.entities.User;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {
    List<Post> findAllByUser(User user);
}
