package socialnetwork.mazkzteam.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import socialnetwork.mazkzteam.model.entities.Photo;

public interface PhotoRepository extends JpaRepository<Photo,Integer> {
    @Transactional
    @Modifying
    @Query(value = "delete from social_network.photo where post_id = ?1",nativeQuery = true)
    void deleteAllByHai(int post_id);
}
