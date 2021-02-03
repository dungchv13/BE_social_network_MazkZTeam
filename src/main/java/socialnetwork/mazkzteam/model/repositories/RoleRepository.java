package socialnetwork.mazkzteam.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import socialnetwork.mazkzteam.model.entities.Role;

public interface RoleRepository extends JpaRepository<Role,Integer> {
}
