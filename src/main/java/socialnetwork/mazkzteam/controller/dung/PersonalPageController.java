package socialnetwork.mazkzteam.controller.dung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import socialnetwork.mazkzteam.model.entities.Comment;
import socialnetwork.mazkzteam.model.entities.Photo;
import socialnetwork.mazkzteam.model.entities.Post;
import socialnetwork.mazkzteam.model.entities.User;
import socialnetwork.mazkzteam.model.service.CommentService;
import socialnetwork.mazkzteam.model.service.PhotoService;
import socialnetwork.mazkzteam.model.service.PostService;
import socialnetwork.mazkzteam.model.service.UserService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/{username}")
@CrossOrigin("*")
public class PersonalPageController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private CommentService commentService;

    @GetMapping
    public User getUser(@PathVariable("username") String username){
        return userService.findUserByUsername(username);
    }

    @GetMapping("/posts")
    public List<Post> getPosts(@PathVariable("username") String username){
        User user = userService.findUserByUsername(username);
        return postService.findAllByUser(user);
    }

    @PostMapping("/create/post")
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
        post1.setUser(user);
        return post1;
    }

    @PostMapping("/create/comment")
    public Comment createComment(@PathVariable("username") String username,@RequestBody Comment comment){
        User user = userService.findUserByUsername(username);
        comment.setUser_id(user.getId());
        comment.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        Comment newComment = commentService.save(comment);
        newComment.setUser(user);
        return newComment;
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
