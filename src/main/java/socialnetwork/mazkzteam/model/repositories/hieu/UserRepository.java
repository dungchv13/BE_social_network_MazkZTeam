package socialnetwork.mazkzteam.model.repositories.hieu;

import org.springframework.data.jpa.repository.JpaRepository;
import socialnetwork.mazkzteam.model.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    //Hieu
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
