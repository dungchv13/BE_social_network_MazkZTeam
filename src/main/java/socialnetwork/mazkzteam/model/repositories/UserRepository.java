package socialnetwork.mazkzteam.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import socialnetwork.mazkzteam.model.entities.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserByUsername(String username);
}
