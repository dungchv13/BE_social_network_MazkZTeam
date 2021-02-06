package socialnetwork.mazkzteam.model.service.hieu;

import org.springframework.security.core.userdetails.UserDetailsService;
import socialnetwork.mazkzteam.model.entities.User;

import java.util.Optional;

public interface IUserService extends IGeneralService<User>, UserDetailsService {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    User saveWithOutEncodePass(User user);
}
