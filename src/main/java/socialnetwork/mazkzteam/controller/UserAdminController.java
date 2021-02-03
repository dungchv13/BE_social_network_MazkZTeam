package socialnetwork.mazkzteam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import socialnetwork.mazkzteam.model.entities.Role;
import socialnetwork.mazkzteam.model.entities.User;
import socialnetwork.mazkzteam.model.service.hieu.IRoleService;
import socialnetwork.mazkzteam.model.service.hieu.IUserService;

import java.sql.Timestamp;
import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin/user")
public class UserAdminController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getUsers() {
        return (List<User>) userService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User getUser(@PathVariable("id") int id) {
        Optional<User> exam = userService.findById(id);
        if (exam.isPresent()) {
            return userService.findById(id).get();
        }
        return null;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if(userService.existsByUsername(user.getUsername())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User newUser = getNewUser(user);
        userService.save(newUser);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") int id) {
        userService.remove(id);
        return new ResponseEntity<>("User has been removed",HttpStatus.OK);
    }
//    @PostMapping("/removeList")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<List<User>> removeExams(@RequestBody List<User> users) {
//        for(User user: users) {
//            userService.remove(user.getId());
//        }
//        return new ResponseEntity<>(users,HttpStatus.OK);
//    }

    @PostMapping("/removeList")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Integer>> removeExams(@RequestBody List<Integer> userIds) {
        for(int userId: userIds) {
            userService.remove(userId);
        }
        return new ResponseEntity<>(userIds,HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateProduct(@PathVariable("id") int id, @RequestBody User user) {
        Optional<User> oldUser = userService.findById(id);
        if (oldUser.isPresent()) {
            oldUser.get().setUsername(user.getUsername());
            oldUser.get().setFirstName(user.getFirstName());
            oldUser.get().setLastName(user.getLastName());
            oldUser.get().setAddress(user.getAddress());
            oldUser.get().setBlocked(user.isBlocked());
            oldUser.get().setDateOfBirth(user.getDateOfBirth());
            oldUser.get().setDetail(user.getDetail());
            oldUser.get().setEmail(user.getEmail());
            oldUser.get().setGender(user.getGender());
            oldUser.get().setPhone(user.getPhone());
            Set<Role> roles = new HashSet<>();
            user.getRoles().forEach(role -> {
                if (role.getName().equals("admin")) {
                    Role adminRole = roleService.findByName("ROLE_ADMIN").get();
                    roles.add(adminRole);
                } else {
                    Role userRole = roleService.findByName("ROLE_USER").get();
                    roles.add(userRole);
                }
            });
            oldUser.get().setRoles(roles);
            if(!user.getPassword().equals(oldUser.get().getPassword())) {
                oldUser.get().setPassword(user.getPassword());
                userService.save(oldUser.get());
            } else {
                userService.saveWithOutEncodePass(oldUser.get());
            }
            return new ResponseEntity<>(oldUser.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private User getNewUser(User user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setAddress(user.getAddress());
        newUser.setAvatar(user.getAvatar());
        newUser.setBlocked(false);
        Date date = new Date();
        Timestamp created_date = new Timestamp(date.getTime());
        newUser.setCreatedDate(created_date);
        newUser.setDateOfBirth(user.getDateOfBirth());
        newUser.setDetail(user.getDetail());
        newUser.setEmail(user.getEmail());
        newUser.setGender(user.getGender());
        newUser.setPhone(user.getPhone());
        Set<Role> roles = new HashSet<>();
        user.getRoles().forEach(role -> {
            if (role.getName().equals("admin")) {
                Role adminRole = roleService.findByName("ROLE_ADMIN").get();
                roles.add(adminRole);
            } else {
                Role userRole = roleService.findByName("ROLE_USER").get();
                roles.add(userRole);
            }
        });
        newUser.setRoles(roles);
        return newUser;
    }

}
