package socialnetwork.mazkzteam.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import socialnetwork.mazkzteam.model.entities.Photo;

public interface PhotoRepository extends JpaRepository<Photo,Integer> {
}
