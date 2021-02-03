package socialnetwork.mazkzteam.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import socialnetwork.mazkzteam.model.entities.User;
import socialnetwork.mazkzteam.model.repositories.UserRepository;
import socialnetwork.mazkzteam.model.service.UserService;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public boolean deleteById(int id) {
        if(findById(id) != null){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }


    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
