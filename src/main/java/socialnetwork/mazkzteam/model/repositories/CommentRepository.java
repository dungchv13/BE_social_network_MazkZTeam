package socialnetwork.mazkzteam.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import socialnetwork.mazkzteam.model.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
}
