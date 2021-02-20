package socialnetwork.mazkzteam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import socialnetwork.mazkzteam.model.entities.Club;
import socialnetwork.mazkzteam.model.entities.Role;
import socialnetwork.mazkzteam.model.entities.User;
import socialnetwork.mazkzteam.model.service.ClubService;
import socialnetwork.mazkzteam.model.service.hieu.IRoleService;
import socialnetwork.mazkzteam.model.service.hieu.IUserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class MazkzteamApplication {
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private ClubService clubService;

    public static void main(String[] args) {
        SpringApplication.run(MazkzteamApplication.class, args);
    }

    @PostConstruct
    public void init() {
        List<User> users = (List<User>) userService.findAll();
        List<Role> roleList = (List<Role>) roleService.findAll();
        List<Club> clubList = clubService.getAll();
        if (roleList.isEmpty()) {
            Role roleAdmin = new Role();
            roleAdmin.setId(1);
            roleAdmin.setName("ROLE_ADMIN");
            roleService.save(roleAdmin);
            Role roleUser = new Role();
            roleUser.setId(2);
            roleUser.setName("ROLE_USER");
            roleService.save(roleUser);
        }
        if (users.isEmpty()) {
            User admin = new User();
            Set<Role> roles = new HashSet<>();
            Role roleAdmin = new Role();
            roleAdmin.setId(1);
            roleAdmin.setName("ROLE_ADMIN");
            roles.add(roleAdmin);
            admin.setUsername("admin");
            admin.setPassword("123456");
            admin.setBlocked(false);
            admin.setRoles(roles);
            userService.save(admin);
        }
        if (clubList.isEmpty()) {
            Club club = new Club();
            club.setId(9999);
            club.setName("defaultGroup");
            club.setFounder_id(1);
            User user = userService.findByUsername("admin").get();
            club.setFounder(user);
            clubService.save(club);
        }
    }

}
