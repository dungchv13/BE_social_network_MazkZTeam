package socialnetwork.mazkzteam.controller.dung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import socialnetwork.mazkzteam.model.entities.User;
import socialnetwork.mazkzteam.model.service.UserService;

@RestController
@RequestMapping("/setting")
@CrossOrigin("*")
public class SettingController {

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") String username){
        return userService.findUserByUsername(username);
    }

    @PutMapping("/update")
    public User updateInfo(@RequestBody User user){
        User userUpdate = userService.findById(user.getId());
        userUpdate.setUsername(user.getUsername());
        userUpdate.setAvatar(user.getAvatar());
        userUpdate.setLastName(user.getLastName());
        userUpdate.setFirstName(user.getFirstName());
        userUpdate.setAddress(user.getAddress());
        userUpdate.setDateOfBirth(user.getDateOfBirth());
        userUpdate.setDetail(user.getDetail());
        userUpdate.setEmail(user.getEmail());
        userUpdate.setGender(user.getGender());
        userUpdate.setPhone(user.getPhone());
        return userService.save(userUpdate);
    }


}
