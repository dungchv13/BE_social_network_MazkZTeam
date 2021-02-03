package socialnetwork.mazkzteam.model.repositories.hieu;

import org.springframework.data.jpa.repository.JpaRepository;
import socialnetwork.mazkzteam.model.entities.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    //Hieu
    Optional<Role> findByName(String name);
}
