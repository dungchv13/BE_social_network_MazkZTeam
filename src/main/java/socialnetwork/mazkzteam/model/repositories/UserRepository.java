package socialnetwork.mazkzteam.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import socialnetwork.mazkzteam.model.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserByUsername(String username);
    //Hieu
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
