package socialnetwork.mazkzteam.model.service.hieu;



import socialnetwork.mazkzteam.model.entities.Role;

import java.util.Optional;

public interface IRoleService extends IGeneralService<Role> {
    Optional<Role> findByName(String name);
}
