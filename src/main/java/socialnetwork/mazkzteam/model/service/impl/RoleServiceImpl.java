package socialnetwork.mazkzteam.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import socialnetwork.mazkzteam.model.entities.Role;
import socialnetwork.mazkzteam.model.repositories.RoleRepository;
import socialnetwork.mazkzteam.model.service.RoleService;

import java.util.List;

public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findById(int id) {
        return roleRepository.findById(id).get();
    }

    @Override
    public boolean deleteById(int id) {
        if(findById(id) != null){
            roleRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
