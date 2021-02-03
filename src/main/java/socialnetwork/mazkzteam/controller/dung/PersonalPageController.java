package socialnetwork.mazkzteam.controller.dung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import socialnetwork.mazkzteam.model.entities.Photo;
import socialnetwork.mazkzteam.model.entities.Post;
import socialnetwork.mazkzteam.model.entities.User;
import socialnetwork.mazkzteam.model.service.PhotoService;
import socialnetwork.mazkzteam.model.service.PostService;
import socialnetwork.mazkzteam.model.service.UserService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/{username}")
public class PersonalPageController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private PhotoService photoService;

    @GetMapping
    public List<Post> detail(@PathVariable("username") String username){
        User user = userService.findUserByUsername(username);
        return postService.findAllByUser(user);
    }

    @PostMapping
    public Post createPost(@PathVariable("username") String username,@RequestBody Post post){
        User user = userService.findUserByUsername(username);
        post.setUser_id(user.getId());
        post.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        Post post1 = postService.save(post);

        List<Photo> photoList = post.getPhotoList();
        for (Photo photo : photoList) {
            photo.setPost_id(post.getId());
        }
        photoService.saveAllPhoto(photoList);

        return post1;
    }

    @PutMapping
    public Post updatePost(@PathVariable("username") String username,@RequestBody Post post){
        User user = userService.findUserByUsername(username);
        Post post1 = postService.findById(post.getId());
        post1.setModifiedAt(Timestamp.valueOf(LocalDateTime.now()));
        post1.setContent(post.getContent());

        return postService.save(post1);
    }
}
