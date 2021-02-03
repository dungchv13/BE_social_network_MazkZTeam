package socialnetwork.mazkzteam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import socialnetwork.mazkzteam.model.Jwt.JwtResponse;
import socialnetwork.mazkzteam.model.entities.Role;
import socialnetwork.mazkzteam.model.entities.User;
import socialnetwork.mazkzteam.model.service.hieu.IRoleService;
import socialnetwork.mazkzteam.model.service.hieu.IUserService;
import socialnetwork.mazkzteam.model.service.hieu.JwtService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin("*")
@RestController
@RequestMapping()
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;


    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody User user) {
        if(userService.existsByUsername(user.getUsername())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User newUser = getNewUser(user);
        userService.save(newUser);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername()).get();
        if (currentUser.isBlocked()) {
            return new ResponseEntity<>("This account was blocked", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), userDetails.getAuthorities()));
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
        newUser.setGender(user.getPhone());
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
